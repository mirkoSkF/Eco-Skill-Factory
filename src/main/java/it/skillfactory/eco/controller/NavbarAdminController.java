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

        NavbarSettings settings = getNavbarSettings();

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

        NavbarSettings settings = getNavbarSettings();

        model.addAttribute("settings", settings);

        return "admin/navbar-management";
    }


    /**
     * ============================================================
     * SALVATAGGIO NUOVA VOCE / MODIFICA
     * ============================================================
     *
     * IMPORTANTE:
     *
     * Quando modifichiamo una voce esistente, NON salviamo
     * direttamente l'oggetto ricevuto dal form.
     *
     * Recuperiamo invece la voce originale dal database
     * e aggiorniamo solamente i campi modificabili.
     *
     * In questo modo la relazione children rimane intatta.
     */
    @PostMapping("/save")
    public String save(
            @ModelAttribute("newItem") NavItem navItem
    ) {

        /*
         * ========================================================
         * NUOVA VOCE
         * ========================================================
         */

        if (navItem.getId() == null) {

            if (navItem.getItemOrder() == null) {
                navItem.setItemOrder(0);
            }

            if (navItem.getColor() != null
                    && navItem.getColor().isBlank()) {

                navItem.setColor(null);
            }

            navItemRepository.save(navItem);

            return "redirect:/admin/navbar";
        }


        /*
         * ========================================================
         * MODIFICA VOCE ESISTENTE
         * ========================================================
         */

        NavItem existingItem = navItemRepository.findById(
                navItem.getId()
        ).orElseThrow(() ->
                new IllegalArgumentException(
                        "ID non valido: " + navItem.getId()
                )
        );


        /*
         * ========================================================
         * AGGIORNIAMO SOLO I CAMPI DEL FORM
         * ========================================================
         *
         * NON tocchiamo:
         *
         * - children
         *
         * La collezione delle voci figlie rimane quella
         * già presente nell'entità originale.
         */


        // Etichetta
        existingItem.setLabel(
                navItem.getLabel()
        );


        // Pagina collegata
        existingItem.setPage(
                navItem.getPage()
        );


        // URL personalizzato
        existingItem.setUrl(
                navItem.getUrl()
        );


        // Menu padre
        existingItem.setParent(
                navItem.getParent()
        );


        // Ordine di visualizzazione
        if (navItem.getItemOrder() == null) {

            existingItem.setItemOrder(0);

        } else {

            existingItem.setItemOrder(
                    navItem.getItemOrder()
            );

        }


        // Colore personalizzato
        if (navItem.getColor() != null
                && navItem.getColor().isBlank()) {

            existingItem.setColor(null);

        } else {

            existingItem.setColor(
                    navItem.getColor()
            );

        }


        // Apertura nuova scheda
        existingItem.setOpenInNewTab(
                navItem.isOpenInNewTab()
        );


        /*
         * ========================================================
         * SALVATAGGIO ENTITÀ ORIGINALE
         * ========================================================
         *
         * La relazione children dell'oggetto esistente
         * non è stata modificata.
         */

        navItemRepository.save(existingItem);


        return "redirect:/admin/navbar";
    }


    /**
     * ============================================================
     * IMPOSTAZIONI GENERALI NAVBAR
     * ============================================================
     *
     * Logo, favicon, opacità e colori.
     */
    @PostMapping("/settings/save")
    public String saveSettings(

            @RequestParam(
                    value = "opacity",
                    defaultValue = "90"
            )
            Integer opacity,

            @RequestParam(
                    value = "bgColor",
                    defaultValue = "#0f172a"
            )
            String bgColor,

            @RequestParam(
                    value = "textColor",
                    defaultValue = "#f1f5f9"
            )
            String textColor,

            @RequestParam(
                    value = "selectedTextColor",
                    defaultValue = "#00dc82"
            )
            String selectedTextColor,

            @RequestParam(
                    value = "dropdownBgColor",
                    defaultValue = "#1e293b"
            )
            String dropdownBgColor,

            @RequestParam(
                    value = "dropdownTextColor",
                    defaultValue = "#f1f5f9"
            )
            String dropdownTextColor,

            @RequestParam(
                    value = "dropdownHoverBgColor",
                    defaultValue = "#00dc82"
            )
            String dropdownHoverBgColor,

            @RequestParam(
                    value = "dropdownSelectedTextColor",
                    defaultValue = "#0f172a"
            )
            String dropdownSelectedTextColor,

            @RequestParam(
                    value = "hideOnScrollBehavior",
                    defaultValue = "NONE"
            )
            String hideOnScrollBehavior,

            @RequestParam(
                    value = "logoFile",
                    required = false
            )
            MultipartFile logoFile,

            @RequestParam(
                    value = "faviconFile",
                    required = false
            )
            MultipartFile faviconFile,

            @RequestParam(
                    value = "removeLogo",
                    required = false,
                    defaultValue = "false"
            )
            boolean removeLogo,

            @RequestParam(
                    value = "removeFavicon",
                    required = false,
                    defaultValue = "false"
            )
            boolean removeFavicon

    ) {

        NavbarSettings settings = getNavbarSettings();


        /*
         * ========================================================
         * VALORI BRAND
         * ========================================================
         */

        settings.setOpacity(opacity);

        settings.setBgColor(bgColor);

        settings.setTextColor(textColor);

        settings.setSelectedTextColor(selectedTextColor);

        settings.setDropdownBgColor(dropdownBgColor);

        settings.setDropdownTextColor(dropdownTextColor);

        settings.setDropdownHoverBgColor(dropdownHoverBgColor);

        settings.setDropdownSelectedTextColor(
                dropdownSelectedTextColor
        );

        settings.setHideOnScrollBehavior(
                hideOnScrollBehavior
        );


        /*
         * ========================================================
         * LOGO
         * ========================================================
         */

        if (removeLogo) {

            deleteUploadedFile(
                    settings.getLogoUrl()
            );

            settings.setLogoUrl(null);

        } else if (
                logoFile != null
                        && !logoFile.isEmpty()
        ) {

            deleteUploadedFile(
                    settings.getLogoUrl()
            );

            String logoUrl = saveUploadedFile(
                    logoFile
            );

            if (logoUrl != null) {

                settings.setLogoUrl(logoUrl);

            }

        }


        /*
         * ========================================================
         * FAVICON
         * ========================================================
         */

        if (removeFavicon) {

            deleteUploadedFile(
                    settings.getFaviconUrl()
            );

            settings.setFaviconUrl(null);

        } else if (
                faviconFile != null
                        && !faviconFile.isEmpty()
        ) {

            deleteUploadedFile(
                    settings.getFaviconUrl()
            );

            String faviconUrl = saveUploadedFile(
                    faviconFile
            );

            if (faviconUrl != null) {

                settings.setFaviconUrl(faviconUrl);

            }

        }


        /*
         * ========================================================
         * SALVATAGGIO IMPOSTAZIONI
         * ========================================================
         */

        navbarSettingsRepository.save(settings);


        return "redirect:/admin/navbar";
    }


    /**
     * ============================================================
     * ELIMINAZIONE VOCE
     * ============================================================
     */
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        navItemRepository.deleteById(id);

        return "redirect:/admin/navbar";
    }


    /**
     * ============================================================
     * RECUPERO IMPOSTAZIONI NAVBAR
     * ============================================================
     */
    private NavbarSettings getNavbarSettings() {

        return navbarSettingsRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {

                    NavbarSettings s = new NavbarSettings();

                    s.setOpacity(90);

                    s.setBgColor("#0f172a");

                    s.setTextColor("#f1f5f9");

                    s.setSelectedTextColor("#00dc82");

                    s.setDropdownBgColor("#1e293b");

                    s.setDropdownTextColor("#f1f5f9");

                    s.setDropdownHoverBgColor("#00dc82");

                    s.setDropdownSelectedTextColor("#0f172a");

                    s.setHideOnScrollBehavior("NONE");

                    return navbarSettingsRepository.save(s);

                });

    }


    /**
     * ============================================================
     * UPLOAD FILE
     * ============================================================
     */
    private String saveUploadedFile(
            MultipartFile file
    ) {

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);

            }

            String originalFilename =
                    file.getOriginalFilename();

            String cleanFileName =
                    originalFilename != null
                            ? originalFilename.replaceAll(
                                    "\\s+",
                                    "_"
                            )
                            : "file";

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + cleanFileName;

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/" + fileName;

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

    }


    /**
     * ============================================================
     * ELIMINAZIONE FILE UPLOAD
     * ============================================================
     */
    private void deleteUploadedFile(
            String relativeUrl
    ) {

        if (
                relativeUrl == null
                        || !relativeUrl.startsWith("/uploads/")
        ) {

            return;

        }

        try {

            String fileName =
                    relativeUrl.replace(
                            "/uploads/",
                            ""
                    );

            Path filePath =
                    Paths.get(UPLOAD_DIR)
                            .resolve(fileName);

            Files.deleteIfExists(filePath);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}