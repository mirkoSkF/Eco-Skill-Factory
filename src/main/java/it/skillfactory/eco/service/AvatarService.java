package it.skillfactory.eco.service;

import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class AvatarService {

    private static final long MAX_AVATAR_SIZE = 5L * 1024L * 1024L;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
        "image/jpeg",
        "image/png",
        "image/webp"
    );

    private final UserRepository userRepository;

    private final Path uploadDirectory;

    public AvatarService(
            UserRepository userRepository,
            @Value("${app.upload.dir:uploads/}")
            String uploadDirectory) {

        this.userRepository = userRepository;
        this.uploadDirectory = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();
    }

    @Transactional
    public User updateAvatar(
            String username,
            MultipartFile file) {

        validateFile(file);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Utente autenticato non trovato."
                        )
                );

        try {

            Files.createDirectories(uploadDirectory);

            String contentType = detectContentType(file);

            String extension = extensionFor(contentType);

            String filename =
                    UUID.randomUUID()
                            + extension;

            Path targetFile =
                    uploadDirectory
                            .resolve(filename)
                            .normalize();

            if (!targetFile.startsWith(uploadDirectory)) {

                throw new AvatarException(
                        "Percorso file non valido."
                );
            }

            try (InputStream inputStream =
                         file.getInputStream()) {

                Files.copy(
                        inputStream,
                        targetFile,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

            String oldAvatarUrl =
                    user.getAvatarUrl();

            String avatarUrl =
                    "/uploads/" + filename;

            user.setAvatarUrl(avatarUrl);

            User savedUser =
                    userRepository.save(user);

            deleteOldAvatar(oldAvatarUrl);

            return savedUser;

        } catch (IOException exception) {

            throw new AvatarException(
                    "Errore durante il salvataggio dell'avatar.",
                    exception
            );
        }
    }

    @Transactional
    public User removeAvatar(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Utente autenticato non trovato."
                        )
                );

        String oldAvatarUrl =
                user.getAvatarUrl();

        user.setAvatarUrl(null);

        User savedUser =
                userRepository.save(user);

        deleteOldAvatar(oldAvatarUrl);

        return savedUser;
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {

            throw new AvatarException(
                    "Seleziona un'immagine."
            );
        }

        if (file.getSize() > MAX_AVATAR_SIZE) {

            throw new AvatarException(
                    "L'avatar non può superare 5 MB."
            );
        }

        String declaredContentType =
                file.getContentType();

        if (declaredContentType == null
                || !ALLOWED_CONTENT_TYPES.contains(
                        declaredContentType.toLowerCase(Locale.ROOT))) {

            throw new AvatarException(
                    "Formato avatar non supportato. "
                    + "Sono consentiti JPEG, PNG e WebP."
            );
        }

        String detectedContentType =
                detectContentType(file);

        if (!ALLOWED_CONTENT_TYPES.contains(
                detectedContentType)) {

            throw new AvatarException(
                    "Il contenuto del file non è "
                    + "un'immagine valida."
            );
        }

        try (InputStream inputStream =
                     file.getInputStream()) {

            BufferedImage image =
                    ImageIO.read(inputStream);

            if (image == null) {

                throw new AvatarException(
                        "Il file non contiene "
                        + "un'immagine valida."
                );
            }

            if (image.getWidth() <= 0
                    || image.getHeight() <= 0) {

                throw new AvatarException(
                        "Dimensioni immagine non valide."
                );
            }

        } catch (IOException exception) {

            throw new AvatarException(
                    "Impossibile verificare l'immagine.",
                    exception
            );
        }
    }

    private String detectContentType(
            MultipartFile file) {

        try (InputStream inputStream =
                     file.getInputStream()) {

            BufferedImage image =
                    ImageIO.read(inputStream);

            if (image == null) {

                throw new AvatarException(
                        "Il file non è un'immagine valida."
                );
            }

            String originalContentType =
                    file.getContentType();

            if (originalContentType == null) {

                throw new AvatarException(
                        "Tipo MIME non riconosciuto."
                );
            }

            String normalized =
                    originalContentType
                            .toLowerCase(Locale.ROOT);

            if (ALLOWED_CONTENT_TYPES.contains(
                    normalized)) {

                return normalized;
            }

            throw new AvatarException(
                    "Tipo MIME non supportato."
            );

        } catch (IOException exception) {

            throw new AvatarException(
                    "Impossibile leggere l'immagine.",
                    exception
            );
        }
    }

    private String extensionFor(
            String contentType) {

        return switch (contentType) {

            case "image/jpeg" ->
                    ".jpg";

            case "image/png" ->
                    ".png";

            case "image/webp" ->
                    ".webp";

            default ->
                    throw new AvatarException(
                            "Formato immagine non supportato."
                    );
        };
    }

    private void deleteOldAvatar(
            String avatarUrl) {

        if (avatarUrl == null
                || avatarUrl.isBlank()) {

            return;
        }

        if (!avatarUrl.startsWith("/uploads/")) {

            return;
        }

        String filename =
                avatarUrl.substring("/uploads/".length());

        if (filename.isBlank()
                || filename.contains("/")
                || filename.contains("\\")
                || filename.contains("..")) {

            return;
        }

        Path oldFile =
                uploadDirectory
                        .resolve(filename)
                        .normalize();

        if (!oldFile.startsWith(uploadDirectory)) {

            return;
        }

        try {

            Files.deleteIfExists(oldFile);

        } catch (IOException exception) {

            // L'eliminazione del vecchio avatar
            // non deve rendere inutilizzabile
            // il nuovo avatar appena salvato.
        }
    }

    public static class AvatarException
            extends RuntimeException {

        public AvatarException(String message) {
            super(message);
        }

        public AvatarException(
                String message,
                Throwable cause) {

            super(message, cause);
        }
    }
}