package it.skillfactory.eco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /*
         * Cartella fisica dove AdminController salva le immagini:
         *
         * uploads/
         *
         * Esempio:
         * C:/mio-progetto/uploads/
         *
         * oppure:
         * /home/user/mio-progetto/uploads/
         */
        Path uploadDir = Paths.get("uploads")
                .toAbsolutePath()
                .normalize();

        /*
         * Converte il percorso fisico in URI file:///
         *
         * Esempio Windows:
         * file:///C:/mio-progetto/uploads/
         *
         * Esempio Linux:
         * file:///home/user/mio-progetto/uploads/
         */
        String uploadPath = uploadDir.toUri().toString();

        /*
         * URL pubblico:
         *
         * /uploads/nome-file.jpg
         *
         * viene cercato fisicamente dentro:
         *
         * uploads/nome-file.jpg
         */
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
