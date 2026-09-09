package it.skillfactory.eco.controller;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Controller
public class ImageConverterController {

    @GetMapping("/admin/image-converter")
    public String showConverterPage() {
        return "admin/image-converter";
    }

    @PostMapping("/api/tools/convert-image")
    @ResponseBody
    public ResponseEntity<byte[]> convertImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetFormat") String targetFormat,
            @RequestParam("width") int width,
            @RequestParam("height") int height,
            @RequestParam(value = "cropX", required = false, defaultValue = "-1") int cropX,
            @RequestParam(value = "cropY", required = false, defaultValue = "-1") int cropY,
            @RequestParam(value = "cropWidth", required = false, defaultValue = "-1") int cropW,
            @RequestParam(value = "cropHeight", required = false, defaultValue = "-1") int cropH) {

        if (file == null || file.isEmpty() || width <= 0 || height <= 0 || width > 10000 || height > 10000) {
            return ResponseEntity.badRequest().build();
        }

        String format = targetFormat == null ? "" : targetFormat.toLowerCase().trim();
        if ("jpeg".equals(format)) {
            format = "jpg";
        }

        if (!format.equals("webp") && !format.equals("png") && !format.equals("jpg") && !format.equals("gif")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            byte[] rawBytes = file.getBytes();
            ImmutableImage image = null;

            try (InputStream is = new ByteArrayInputStream(rawBytes)) {
                image = ImmutableImage.loader().fromStream(is);
            } catch (Exception e) {
                try (InputStream isFallback = new ByteArrayInputStream(rawBytes)) {
                    BufferedImage bufferedImg = ImageIO.read(isFallback);
                    if (bufferedImg != null) {
                        image = ImmutableImage.fromAwt(bufferedImg);
                    }
                }
            }

            if (image == null) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            // 1. Ritaglio (Crop) usando subimage (compatibile con Scrimage 4+)
            if (cropX >= 0 && cropY >= 0 && cropW > 0 && cropH > 0) {
                // Assicuriamoci che i limiti non eccedano le dimensioni reali dell'immagine
                int validX = Math.min(cropX, image.width - 1);
                int validY = Math.min(cropY, image.height - 1);
                int validW = Math.min(cropW, image.width - validX);
                int validH = Math.min(cropH, image.height - validY);

                if (validW > 0 && validH > 0) {
                    image = image.subimage(validX, validY, validW, validH);
                }
            }

            // 2. Ridimensionamento (Scale)
            image = image.scaleTo(width, height);

            // 3. Generazione Output
            byte[] imageBytes;
            if ("webp".equals(format)) {
                imageBytes = image.bytes(WebpWriter.DEFAULT);
            } else {
                BufferedImage resizedImage = image.awt();
                boolean supportsAlpha = format.equals("png") || format.equals("gif");

                if (!supportsAlpha && resizedImage.getColorModel().hasAlpha()) {
                    BufferedImage rgbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = rgbImage.createGraphics();
                    try {
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, 0, width, height);
                        g2d.drawImage(resizedImage, 0, 0, null);
                    } finally {
                        g2d.dispose();
                    }
                    resizedImage = rgbImage;
                }

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    boolean written = ImageIO.write(resizedImage, format, baos);
                    if (!written) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                    imageBytes = baos.toByteArray();
                }
            }

            if (imageBytes == null || imageBytes.length == 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            MediaType mediaType = switch (format) {
                case "png" -> MediaType.IMAGE_PNG;
                case "jpg" -> MediaType.IMAGE_JPEG;
                case "gif" -> MediaType.IMAGE_GIF;
                case "webp" -> MediaType.parseMediaType("image/webp");
                default -> MediaType.APPLICATION_OCTET_STREAM;
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentLength(imageBytes.length);
            headers.setContentDispositionFormData("attachment", "converted_image." + format);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
