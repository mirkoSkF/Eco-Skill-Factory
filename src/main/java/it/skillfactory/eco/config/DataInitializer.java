package it.skillfactory.eco.config;

import it.skillfactory.eco.model.Role;
import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.RoleRepository;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeSecurityData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            /*
             * =====================================================
             * RUOLO ADMIN
             * =====================================================
             */
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("ADMIN")
                                    .description("Amministratore del CMS")
                                    .canAccessAdminSidebar(true)
                                    .build()
                    ));

            /*
             * =====================================================
             * RUOLO USER
             * =====================================================
             */
            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(
                            Role.builder()
                                    .name("USER")
                                    .description("Utente standard")
                                    .canAccessAdminSidebar(false)
                                    .build()
                    ));

            /*
             * =====================================================
             * UTENTE ADMIN
             * =====================================================
             *
             * L'utente viene creato solamente se non esiste.
             *
             * Questo evita di sovrascrivere un eventuale admin
             * già presente nel database.
             */
            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .email("admin@localhost")
                        .password(passwordEncoder.encode("admin"))
                        .firstName("Admin")
                        .lastName("CMS")
                        .enabled(true)
                        .roles(new HashSet<>())
                        .build();

                admin.getRoles().add(adminRole);

                userRepository.save(admin);
            }

            /*
             * =====================================================
             * PROTEZIONE DA DATI INCOMPLETI
             * =====================================================
             *
             * Se un admin esiste già ma non ha ruoli, gli assegniamo
             * il ruolo ADMIN.
             *
             * Non modifichiamo password, email o altri dati.
             */
            userRepository.findByUsername("admin")
                    .ifPresent(admin -> {

                        if (admin.getRoles() == null) {
                            admin.setRoles(new HashSet<>());
                        }

                        boolean hasAdminRole = admin.getRoles()
                                .stream()
                                .anyMatch(role ->
                                        "ADMIN".equalsIgnoreCase(role.getName())
                                );

                        if (!hasAdminRole) {
                            admin.getRoles().add(adminRole);
                            userRepository.save(admin);
                        }
                    });
        };
    }
}
