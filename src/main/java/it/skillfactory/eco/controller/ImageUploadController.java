package it.skillfactory.eco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class ImageUploadController {

    // Percorso assoluto o relativo dove salvare le immagini
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/immagine")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "File vuoto"));
        }

        try {
            // 1. Crea la cartella se non esiste
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. Genera un nome unico per il file per evitare sovrascritture
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + extension;

            // 3. Salva il file nel percorso di destinazione
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // 4. Ritorna l'URL accessibile lato client (ad es. /uploads/nomefile.png)
            String fileUrl = "/uploads/" + newFilename;
            return ResponseEntity.ok(Map.of("location", fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("message", "Errore durante il salvataggio: " + e.getMessage()));
        }
    }
}