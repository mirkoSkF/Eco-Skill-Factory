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
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageConverterController {

    /**
     * Pagina del convertitore immagini.
     * URL: /admin/image-converter
     */
    @GetMapping("/admin/image-converter")
    public String showConverterPage() {
        return "admin/image-converter";
    }

    /**
     * Conversione e ridimensionamento immagine.
     * Endpoint: POST /api/tools/convert-image
     */
    @PostMapping("/api/tools/convert-image")
    @ResponseBody
    public ResponseEntity<byte[]> convertImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetFormat") String targetFormat,
            @RequestParam("width") int width,
            @RequestParam("height") int height) {

        // ---------------------------------------------------------
        // VALIDAZIONE FILE E DIMENSIONI
        // ---------------------------------------------------------
        if (file == null || file.isEmpty() || width <= 0 || height <= 0 || width > 10000 || height > 10000) {
            return ResponseEntity.badRequest().build();
        }

        // ---------------------------------------------------------
        // NORMALIZZAZIONE E VALIDAZIONE FORMATO TARGET
        // ---------------------------------------------------------
        String format = targetFormat == null ? "" : targetFormat.toLowerCase().trim();

        if ("jpeg".equals(format)) {
            format = "jpg";
        }

        // Accetta esclusivamente WebP, PNG, JPG e GIF
        if (!format.equals("webp") && !format.equals("png") && !format.equals("jpg") && !format.equals("gif")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            byte[] rawBytes = file.getBytes();
            ImmutableImage image = null;

            // -----------------------------------------------------
            // 1. CARICAMENTO E SCALING DIRETTO CON scaleTo (SOLUZIONE 1)
            // scaleTo adatta l'immagine esattamente a width e height senza aggiungere bordi
            // -----------------------------------------------------
            try (InputStream is = new ByteArrayInputStream(rawBytes)) {
                image = ImmutableImage.loader().fromStream(is).scaleTo(width, height);
            } catch (Exception e) {
                // Fallback con ImageIO per casi o formati specifici non letti al primo colpo
                try (InputStream isFallback = new ByteArrayInputStream(rawBytes)) {
                    BufferedImage bufferedImg = ImageIO.read(isFallback);
                    if (bufferedImg != null) {
                        image = ImmutableImage.fromAwt(bufferedImg).scaleTo(width, height);
                    }
                }
            }

            if (image == null) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
            }

            // -----------------------------------------------------
            // 2. SCRITTURA NEL FORMATO DI DESTINAZIONE
            // -----------------------------------------------------
            byte[] imageBytes;

            if ("webp".equals(format)) {
                imageBytes = image.bytes(WebpWriter.DEFAULT);
            } else {
                BufferedImage resizedImage = image.awt();
                boolean supportsAlpha = format.equals("png") || format.equals("gif");

                // Se il formato di destinazione non supporta la trasparenza (es. JPG),
                // applichiamo lo sfondo bianco sull'immagine già scalata a dimensione esatta.
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

            // -----------------------------------------------------
            // 3. GENERAZIONE HEADERS HTTP E CONTENT TYPE
            // -----------------------------------------------------
            MediaType mediaType;
            switch (format) {
                case "png":
                    mediaType = MediaType.IMAGE_PNG;
                    break;
                case "jpg":
                    mediaType = MediaType.IMAGE_JPEG;
                    break;
                case "gif":
                    mediaType = MediaType.IMAGE_GIF;
                    break;
                case "webp":
                    mediaType = MediaType.parseMediaType("image/webp");
                    break;
                default:
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                    break;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentLength(imageBytes.length);
            headers.setContentDispositionFormData("attachment", "converted_image." + format);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            System.err.println("Errore I/O durante la conversione dell'immagine:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            System.err.println("Errore inatteso durante la conversione dell'immagine:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
