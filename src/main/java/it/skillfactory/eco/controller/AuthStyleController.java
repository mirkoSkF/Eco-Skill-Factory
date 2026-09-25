package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.AuthStyleSettings;
import it.skillfactory.eco.repository.AuthStyleSettingsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/auth-style")
public class AuthStyleController {

    private final AuthStyleSettingsRepository authStyleSettingsRepository;

    public AuthStyleController(
            AuthStyleSettingsRepository authStyleSettingsRepository) {

        this.authStyleSettingsRepository = authStyleSettingsRepository;
    }

    @GetMapping
    public String authStylePage(Model model) {

        AuthStyleSettings settings = getOrCreateSettings();

        model.addAttribute("settings", settings);

        return "admin/auth-style";
    }

    @PostMapping("/save")
    public String saveSettings(
            @RequestParam(required = false) String profileBackgroundColor,
            @RequestParam(required = false) String profileFormBackgroundColor,
            @RequestParam(required = false) String profileInputBackgroundColor,
            @RequestParam(required = false) String profileLabelColor,
            @RequestParam(required = false) String profilePlaceholderColor,

            @RequestParam(required = false) String loginBackgroundColor,
            @RequestParam(required = false) String loginFormBackgroundColor,
            @RequestParam(required = false) String loginInputBackgroundColor,
            @RequestParam(required = false) String loginLabelColor,
            @RequestParam(required = false) String loginPlaceholderColor) {

        AuthStyleSettings settings = getOrCreateSettings();

        /*
         * =========================================================
         * PROFILO
         * =========================================================
         *
         * Aggiorniamo esclusivamente i valori ricevuti.
         * I colori Login non vengono toccati.
         */

        if (isValidColor(profileBackgroundColor)) {
            settings.setProfileBackgroundColor(profileBackgroundColor);
        }

        if (isValidColor(profileFormBackgroundColor)) {
            settings.setProfileFormBackgroundColor(profileFormBackgroundColor);
        }

        if (isValidColor(profileInputBackgroundColor)) {
            settings.setProfileInputBackgroundColor(profileInputBackgroundColor);
        }

        if (isValidColor(profileLabelColor)) {
            settings.setProfileLabelColor(profileLabelColor);
        }

        if (isValidColor(profilePlaceholderColor)) {
            settings.setProfilePlaceholderColor(profilePlaceholderColor);
        }

        /*
         * =========================================================
         * LOGIN
         * =========================================================
         *
         * Aggiorniamo esclusivamente i valori ricevuti.
         * I colori Profilo non vengono toccati.
         */

        if (isValidColor(loginBackgroundColor)) {
            settings.setLoginBackgroundColor(loginBackgroundColor);
        }

        if (isValidColor(loginFormBackgroundColor)) {
            settings.setLoginFormBackgroundColor(loginFormBackgroundColor);
        }

        if (isValidColor(loginInputBackgroundColor)) {
            settings.setLoginInputBackgroundColor(loginInputBackgroundColor);
        }

        if (isValidColor(loginLabelColor)) {
            settings.setLoginLabelColor(loginLabelColor);
        }

        if (isValidColor(loginPlaceholderColor)) {
            settings.setLoginPlaceholderColor(loginPlaceholderColor);
        }

        authStyleSettingsRepository.save(settings);

        return "redirect:/admin/auth-style?saved=true";
    }

    private AuthStyleSettings getOrCreateSettings() {

        return authStyleSettingsRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() ->
                        authStyleSettingsRepository.save(
                                new AuthStyleSettings()
                        )
                );
    }

    private boolean isValidColor(String color) {

        if (color == null || color.isBlank()) {
            return false;
        }

        return color.matches("^#[0-9A-Fa-f]{6}$");
    }
}