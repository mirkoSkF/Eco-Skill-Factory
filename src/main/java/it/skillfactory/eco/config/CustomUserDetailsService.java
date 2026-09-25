package it.skillfactory.eco.config;

import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Gestione account ADMIN statico/fallback
        if ("admin".equalsIgnoreCase(username)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username("admin")
                    .password("$2a$10$eE04f/H/pZ/m/a1xL9xY.eD04FvX3xYx5O8wXGf6l/zT9iY.XkC6W") // Hashed "admin"
                    .roles("ADMIN")
                    .build();
        }

        // Cerca l'utente registrato nel Database tramite Email
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}