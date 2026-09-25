package it.skillfactory.eco.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class ImageUploadController {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @PostMapping("/immagine")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "File vuoto"));
        }

        try {

            Path uploadPath = Paths.get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();

            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename
                        .substring(originalFilename.lastIndexOf("."))
                        .toLowerCase();
            }

            String newFilename = UUID.randomUUID() + extension;

            Path targetLocation = uploadPath
                    .resolve(newFilename)
                    .normalize();

            if (!targetLocation.startsWith(uploadPath)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Percorso file non valido"));
            }

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String fileUrl = "/uploads/" + newFilename;

            return ResponseEntity.ok(
                    Map.of("location", fileUrl)
            );

        } catch (IOException e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "message",
                            "Errore durante il salvataggio: " + e.getMessage()
                    ));
        }
    }
}