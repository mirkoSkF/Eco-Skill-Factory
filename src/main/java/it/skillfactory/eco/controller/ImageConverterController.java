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
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (width <= 0 || height <= 0 || width > 10000 || height > 10000) {
            return ResponseEntity.badRequest().build();
        }

        // ---------------------------------------------------------
        // NORMALIZZAZIONE FORMATO
        // ---------------------------------------------------------
        String format = targetFormat == null ? "" : targetFormat.toLowerCase().trim();

        if ("jpeg".equals(format)) {
            format = "jpg";
        }

        if (!format.equals("webp") && !format.equals("png") && !format.equals("jpg") && !format.equals("gif") && !format.equals("bmp")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Uso del try-with-resources per CHIUDERE AUTOMATICAMENTE lo stream di input del file
        try (InputStream inputStream = file.getInputStream()) {

            byte[] imageBytes;

            // -----------------------------------------------------
            // 1. CARICAMENTO E RIDIMENSIONAMENTO CON UNIFICATO CON SCRIMAGE
            // Scrimage legge in nativo sia WebP che PNG, JPG, GIF e BMP
            // -----------------------------------------------------
            ImmutableImage image = ImmutableImage.loader()
                    .fromStream(inputStream)
                    .resizeTo(width, height);

            // -----------------------------------------------------
            // 2. SCRITTURA NEL FORMATO TARGET
            // -----------------------------------------------------
            if ("webp".equals(format)) {
                
                // Conversione in formato WebP via Scrimage
                imageBytes = image.bytes(WebpWriter.DEFAULT);

            } else {
                
                // Estragga l'immagine AWT gestita da Scrimage per gli altri formati
                BufferedImage resizedImage = image.awt();
                boolean supportsAlpha = format.equals("png") || format.equals("gif");

                // Se la destinazione non supporta l'alfa (es. JPG), imposta sfondo bianco per evitare artefatti neri
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

                // Scrittura stream di output mediante ImageIO
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
            // 3. CONTENT TYPE E HEADERS HTTP
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
                case "bmp":
                    mediaType = MediaType.parseMediaType("image/bmp");
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