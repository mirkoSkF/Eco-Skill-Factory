package it.skillfactory.eco.service;

import it.skillfactory.eco.model.Role;
import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Utente non trovato: " + username
                        )
                );

        Collection<? extends GrantedAuthority> authorities =
                user.getRoles()
                        .stream()
                        .map(this::mapRoleToAuthority)
                        .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.isEnabled())
                .build();
    }

    private GrantedAuthority mapRoleToAuthority(Role role) {

        String roleName = role.getName();

        if (roleName == null || roleName.isBlank()) {
            throw new IllegalStateException(
                    "Il ruolo dell'utente non può avere un nome vuoto"
            );
        }

        String authorityName = roleName.startsWith("ROLE_")
                ? roleName
                : "ROLE_" + roleName;

        return new SimpleGrantedAuthority(authorityName);
    }
}
