package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.NavItem;
import it.skillfactory.eco.model.NavbarSettings;
import it.skillfactory.eco.repository.NavItemRepository;
import it.skillfactory.eco.repository.NavbarSettingsRepository;
import it.skillfactory.eco.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin/navbar")
public class NavbarAdminController {

    @Autowired
    private NavItemRepository navItemRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private NavbarSettingsRepository navbarSettingsRepository;

    private static final String UPLOAD_DIR = "uploads/";

    /**
     * Pagina gestione navbar
     */
    @GetMapping
    public String index(Model model) {

        model.addAttribute(
            "navItems",
            navItemRepository.findByParentIsNullOrderByItemOrderAsc()
        );

        model.addAttribute(
            "pages",
            pageRepository.findAll()
        );

        NavItem newItem = new NavItem();
        newItem.setOpenInNewTab(false);
        model.addAttribute("newItem", newItem);

        NavbarSettings settings = navbarSettingsRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    NavbarSettings s = new NavbarSettings();
                    s.setOpacity(90);
                    return navbarSettingsRepository.save(s);
                });
        model.addAttribute("settings", settings);

        return "admin/navbar-management";
    }

    /**
     * Modifica voce esistente
     */
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model
    ) {

        NavItem item = navItemRepository.findById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "ID non valido: " + id
                )
            );

        model.addAttribute(
            "navItems",
            navItemRepository.findByParentIsNullOrderByItemOrderAsc()
        );

        model.addAttribute(
            "pages",
            pageRepository.findAll()
        );

        model.addAttribute("newItem", item);

        NavbarSettings settings = navbarSettingsRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    NavbarSettings s = new NavbarSettings();
                    s.setOpacity(90);
                    return navbarSettingsRepository.save(s);
                });
        model.addAttribute("settings", settings);

        return "admin/navbar-management";
    }

    /**
     * Salvataggio nuova voce / modifica
     */
    @PostMapping("/save")
    public String save(
            @ModelAttribute("newItem") NavItem navItem
    ) {

        if (navItem.getItemOrder() == null) {
            navItem.setItemOrder(0);
        }

        navItemRepository.save(navItem);

        return "redirect:/admin/navbar";
    }

    /**
     * Salvataggio impostazioni generali Navbar (Logo, Favicon, Opacità)
     */
    @PostMapping("/settings/save")
    public String saveSettings(
            @RequestParam(value = "opacity", defaultValue = "90") Integer opacity,
            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
            @RequestParam(value = "faviconFile", required = false) MultipartFile faviconFile,
            @RequestParam(value = "removeLogo", required = false, defaultValue = "false") boolean removeLogo,
            @RequestParam(value = "removeFavicon", required = false, defaultValue = "false") boolean removeFavicon
    ) {

        NavbarSettings settings = navbarSettingsRepository.findAll().stream()
                .findFirst()
                .orElseGet(NavbarSettings::new);

        settings.setOpacity(opacity);

        if (removeLogo) {
            deleteUploadedFile(settings.getLogoUrl());
            settings.setLogoUrl(null);
        } else if (logoFile != null && !logoFile.isEmpty()) {
            deleteUploadedFile(settings.getLogoUrl());
            String logoUrl = saveUploadedFile(logoFile);
            if (logoUrl != null) {
                settings.setLogoUrl(logoUrl);
            }
        }

        if (removeFavicon) {
            deleteUploadedFile(settings.getFaviconUrl());
            settings.setFaviconUrl(null);
        } else if (faviconFile != null && !faviconFile.isEmpty()) {
            deleteUploadedFile(settings.getFaviconUrl());
            String faviconUrl = saveUploadedFile(faviconFile);
            if (faviconUrl != null) {
                settings.setFaviconUrl(faviconUrl);
            }
        }

        navbarSettingsRepository.save(settings);

        return "redirect:/admin/navbar";
    }

    /**
     * Eliminazione voce
     */
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        navItemRepository.deleteById(id);

        return "redirect:/admin/navbar";
    }

    private String saveUploadedFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = file.getOriginalFilename();
            String cleanFileName = originalFilename != null
                    ? originalFilename.replaceAll("\\s+", "_")
                    : "file";
            String fileName = UUID.randomUUID() + "_" + cleanFileName;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteUploadedFile(String relativeUrl) {
        if (relativeUrl == null || !relativeUrl.startsWith("/uploads/")) {
            return;
        }
        try {
            String fileName = relativeUrl.replace("/uploads/", "");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}