package it.skillfactory.eco.service;

import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Questa email risulta già registrata.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    public void updateProfile(String email,
                              String nome,
                              String cognome,
                              MultipartFile foto) throws IOException {

        User user = getByEmail(email);

        user.setNome(nome);
        user.setCognome(cognome);

        if (foto != null && !foto.isEmpty()) {

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = foto.getOriginalFilename();

            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(
                        originalFilename.lastIndexOf(".")
                ).toLowerCase();
            }

            String fileName = UUID.randomUUID() + extension;

            Path filePath = uploadPath.resolve(fileName).normalize();

            // Sicurezza: il file deve rimanere dentro la cartella upload
            if (!filePath.startsWith(uploadPath)) {
                throw new IOException("Percorso file non valido.");
            }

            // Se esiste già un avatar, eliminiamolo
            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isBlank()) {

                String oldFileName = user.getAvatarUrl();

                if (oldFileName.startsWith("/uploads/")) {
                    oldFileName = oldFileName.substring("/uploads/".length());
                }

                Path oldFile = uploadPath.resolve(oldFileName).normalize();

                if (oldFile.startsWith(uploadPath) && Files.exists(oldFile)) {
                    Files.deleteIfExists(oldFile);
                }
            }

            Files.copy(
                    foto.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Nel database salviamo l'URL web, NON il percorso Docker
            user.setAvatarUrl("/uploads/" + fileName);
        }

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}