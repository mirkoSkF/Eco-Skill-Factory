package it.skillfactory.eco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

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
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/tinymce/**",
                    "/uploads/**",
                    "/api/uploads/**",
                    "/favicon.ico",
                    "/p/**"
                ).permitAll()


                /*
                 * =====================================================
                 * AREA AMMINISTRATIVA
                 * =====================================================
                 *
                 * Tutto /admin/** richiede ruolo ADMIN.
                 */

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")


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

                .defaultSuccessUrl(
                    "/admin/dashboard",
                    true
                )

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
             *
             * Attualmente disabilitato perché il tuo upload TinyMCE
             * utilizza una POST verso /api/uploads/immagine senza
             * passare il token CSRF.
             */

            .csrf(csrf -> csrf.disable())


            /*
             * =========================================================
             * IFRAME / TINYMCE
             * =========================================================
             */

            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );


        return http.build();
    }


    /*
     * =============================================================
     * UTENTE ADMIN
     * =============================================================
     */

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder) {

        UserDetails admin =
                User.builder()
                    .username("admin")
                    .password(
                        encoder.encode("admin")
                    )
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
