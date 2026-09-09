package it.skillfactory.eco.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ImageConverterController {

    // Recupera il percorso della directory di upload (uguale a /api/uploads/immagine)
    @Value("${upload.directory:uploads/}")
    private String uploadDirectory;

    @GetMapping("/admin/image-converter")
    public String showConverterPage(Model model) {
        model.addAttribute("activeMenu", "image-converter");
        return "admin/image-converter";
    }

    @PostMapping("/api/tools/convert-image")
    @ResponseBody
    public ResponseEntity<?> convertImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetFormat") String targetFormat,
            @RequestParam("width") Integer width,
            @RequestParam("height") Integer height,
            @RequestParam(value = "saveToServer", defaultValue = "false") boolean saveToServer
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File non presente");
            }

            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                return ResponseEntity.badRequest().body("Formato immagine non valido");
            }

            int finalWidth = (width != null && width > 0) ? width : originalImage.getWidth();
            int finalHeight = (height != null && height > 0) ? height : originalImage.getHeight();

            int imageType = "jpg".equalsIgnoreCase(targetFormat) || "jpeg".equalsIgnoreCase(targetFormat)
                    ? BufferedImage.TYPE_INT_RGB 
                    : BufferedImage.TYPE_INT_ARGB;

            BufferedImage resizedImage = new BufferedImage(finalWidth, finalHeight, imageType);
            Graphics2D g2d = resizedImage.createGraphics();

            if (imageType == BufferedImage.TYPE_INT_RGB) {
                g2d.setColor(java.awt.Color.WHITE);
                g2d.fillRect(0, 0, finalWidth, finalHeight);
            }

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.drawImage(originalImage, 0, 0, finalWidth, finalHeight, null);
            g2d.dispose();

            String formatName = targetFormat.toLowerCase();
            if ("jpg".equals(formatName)) formatName = "jpeg";

            // CASO A: Salvataggio direttamente sul Server
            if (saveToServer) {
                Path uploadPath = Paths.get(uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String newFilename = UUID.randomUUID().toString() + "." + targetFormat.toLowerCase();
                File destFile = uploadPath.resolve(newFilename).toFile();

                boolean written = ImageIO.write(resizedImage, formatName, destFile);
                if (!written) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Formato non supportato");
                }

                // Genera la risposta JSON con l'URL statico del file salvato
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("location", "/uploads/" + newFilename);
                response.put("filename", newFilename);

                return ResponseEntity.ok(response);
            } 
            
            // CASO B: Download immediato nel browser
            else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                boolean written = ImageIO.write(resizedImage, formatName, baos);
                if (!written) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
                }

                byte[] imageBytes = baos.toByteArray();
                String originalFilename = file.getOriginalFilename();
                String baseName = (originalFilename != null && originalFilename.contains(".")) 
                        ? originalFilename.substring(0, originalFilename.lastIndexOf('.')) 
                        : "immagine_convertita";
                String downloadFilename = baseName + "_resized." + targetFormat.toLowerCase();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(getMediaTypeForFormat(targetFormat));
                headers.setContentDispositionFormData("attachment", downloadFilename);

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore salvataggio file");
        }
    }

    private MediaType getMediaTypeForFormat(String format) {
        switch (format.toLowerCase()) {
            case "png": return MediaType.IMAGE_PNG;
            case "gif": return MediaType.IMAGE_GIF;
            case "webp": return MediaType.parseMediaType("image/webp");
            case "jpg":
            case "jpeg": 
            default: return MediaType.IMAGE_JPEG;
        }
    }
}