package it.skillfactory.eco.service;

import it.skillfactory.eco.dto.ProfileUpdateRequest;
import it.skillfactory.eco.dto.RegistrationRequest;
import it.skillfactory.eco.model.Role;
import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.RoleRepository;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegistrationRequest request) {

        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByUsername(username)) {
            throw new RegistrationException(
                    "username",
                    "Lo username è già utilizzato."
            );
        }

        if (userRepository.existsByEmail(email)) {
            throw new RegistrationException(
                    "email",
                    "L'indirizzo email è già utilizzato."
            );
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RegistrationException(
                    "confirmPassword",
                    "Le password non coincidono."
            );
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Il ruolo USER non è presente nel database."
                        )
                );

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .enabled(true)
                .roles(new HashSet<>())
                .build();

        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(
            String authenticatedUsername,
            ProfileUpdateRequest request) {

        User user = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Utente autenticato non trovato."
                        )
                );

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailAndIdNot(
                email,
                user.getId())) {

            throw new ProfileUpdateException(
                    "email",
                    "L'indirizzo email è già utilizzato da un altro utente."
            );
        }

        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setEmail(email);

        return userRepository.save(user);
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Utente autenticato non trovato."
                        )
                );
    }

    public static class RegistrationException
            extends RuntimeException {

        private final String field;

        public RegistrationException(
                String field,
                String message) {

            super(message);
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }

    public static class ProfileUpdateException
            extends RuntimeException {

        private final String field;

        public ProfileUpdateException(
                String field,
                String message) {

            super(message);
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }
}
