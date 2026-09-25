package it.skillfactory.eco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     * =============================================================
     * 1. CATENA DI SICUREZZA PER L'ADMIN
     * =============================================================
     */
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(
            HttpSecurity http) throws Exception {

        UserDetailsService adminDetailsService =
                adminUserDetailsService(passwordEncoder());

        http
            /*
             * Questa catena gestisce SOLO:
             *
             * /admin/**
             * /admin-login
             * /login
             * /login.html
             */
            .securityMatcher(
                "/admin/**",
                "/admin-login",
                "/login",
                "/login.html"
            )

            .authorizeHttpRequests(auth -> auth

                /*
                 * Pagine pubbliche del login amministratore
                 */
                .requestMatchers(
                    "/admin-login",
                    "/login",
                    "/login.html",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                /*
                 * Tutta l'area amministrativa richiede ROLE_ADMIN
                 */
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
            )

            /*
             * =====================================================
             * LOGIN ADMIN
             * =====================================================
             */
            .formLogin(form -> form

                /*
                 * Pagina visualizzata
                 */
                .loginPage("/admin-login")

                /*
                 * URL al quale viene inviato il form
                 */
                .loginProcessingUrl("/admin-login")

                /*
                 * Login corretto
                 */
                .defaultSuccessUrl("/admin/dashboard", true)

                /*
                 * Login errato
                 */
                .failureUrl("/admin-login?error=true")

                .permitAll()
            )

            /*
             * IMPORTANTISSIMO:
             *
             * L'admin viene cercato esclusivamente
             * nell'InMemoryUserDetailsManager.
             *
             * Gli utenti normali del database NON vengono
             * utilizzati per il login amministrativo.
             */
            .userDetailsService(adminDetailsService)

            /*
             * REMEMBER ME ADMIN
             */
            .rememberMe(remember -> remember
                .userDetailsService(adminDetailsService)
                .key("ecoAdminRememberMeKey")
                .tokenValiditySeconds(86400 * 14)
                .rememberMeParameter("remember-me")
            )

            /*
             * LOGOUT ADMIN
             */
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            .csrf(csrf -> csrf.disable())

            .headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }


    /*
     * =============================================================
     * 2. CATENA DI SICUREZZA PER UTENTI / SITO PUBBLICO
     * =============================================================
     */
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(
            HttpSecurity http,
            UserDetailsService customUserDetailsService) throws Exception {

        http

            .authorizeHttpRequests(auth -> auth

                /*
                 * =================================================
                 * PAGINE PUBBLICHE
                 * =================================================
                 */
                .requestMatchers(
                    "/",
                    "/login-user",
                    "/register",
                    "/forgot-password",
                    "/reset-password",
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
                 * =================================================
                 * AREA UTENTE
                 * =================================================
                 */
                .requestMatchers("/user/**")
                .authenticated()

                /*
                 * Tutto il resto richiede autenticazione
                 */
                .anyRequest()
                .authenticated()
            )

            /*
             * =====================================================
             * LOGIN UTENTE
             * =====================================================
             */
            .formLogin(form -> form

                /*
                 * Pagina HTML del login utente
                 */
                .loginPage("/login-user")

                /*
                 * Endpoint al quale viene inviato il form
                 */
                .loginProcessingUrl("/login-user")

                /*
                 * Login riuscito
                 */
                .defaultSuccessUrl("/user/dashboard", true)

                /*
                 * Login fallito
                 */
                .failureUrl("/login-user?error=true")

                .permitAll()
            )

            /*
             * UserDetailsService del database
             */
            .userDetailsService(customUserDetailsService)

            /*
             * REMEMBER ME UTENTI
             */
            .rememberMe(remember -> remember
                .userDetailsService(customUserDetailsService)
                .key("ecoUserRememberMeKey")
                .tokenValiditySeconds(86400 * 14)
                .rememberMeParameter("remember-me")
            )

            /*
             * LOGOUT UTENTE
             */
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-user?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            .csrf(csrf -> csrf.disable())

            .headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }


    /*
     * =============================================================
     * UTENTE ADMIN
     * =============================================================
     *
     * Per il momento l'admin rimane quello già presente
     * nella configurazione originale.
     */
    private UserDetailsService adminUserDetailsService(
            PasswordEncoder encoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
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
}

