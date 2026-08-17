package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.NavbarSettings;
import it.skillfactory.eco.repository.NavbarSettingsRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final NavbarSettingsRepository settingsRepository;

    public GlobalControllerAdvice(NavbarSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @ModelAttribute("navbarSettings")
    public NavbarSettings getNavbarSettings() {
        return settingsRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    NavbarSettings defaultSettings = new NavbarSettings();
                    defaultSettings.setOpacity(90);
                    return defaultSettings;
                });
    }
}