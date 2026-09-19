package it.skillfactory.eco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler authenticationSuccessHandler)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                /*
                 * =====================================================
                 * RISORSE PUBBLICHE
                 * =====================================================
                 */
                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/tinymce/**",
                    "/uploads/**",
                    "/fonts/**",
                    "/api/uploads/**",
                    "/favicon.ico",
                    "/p/**"
                ).permitAll()

                /*
                 * =====================================================
                 * AREA AMMINISTRATIVA
                 * =====================================================
                 */
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                /*
                 * =====================================================
                 * AREA UTENTE
                 * =====================================================
                 */
                .requestMatchers("/user/**")
                .authenticated()

                /*
                 * =====================================================
                 * TUTTO IL RESTO
                 * =====================================================
                 */
                .anyRequest()
                .authenticated()
            )

            /*
             * =========================================================
             * LOGIN
             * =========================================================
             */
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
            )

            /*
             * =========================================================
             * LOGOUT
             * =========================================================
             */
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )

            /*
             * =========================================================
             * CSRF
             * =========================================================
             */
            .csrf(csrf -> csrf.disable())

            /*
             * =========================================================
             * HEADERS
             * =========================================================
             */
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    /*
     * =============================================================
     * PASSWORD ENCODER
     * =============================================================
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * =============================================================
     * AUTHENTICATION SUCCESS HANDLER
     * =============================================================
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin = authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority ->
                            "ROLE_ADMIN".equals(
                                    authority.getAuthority()
                            )
                    );

            if (isAdmin) {

                response.sendRedirect(
                        "/admin/dashboard"
                );

                return;
            }

            response.sendRedirect(
                    "/user/profile"
            );
        };
    }
}
