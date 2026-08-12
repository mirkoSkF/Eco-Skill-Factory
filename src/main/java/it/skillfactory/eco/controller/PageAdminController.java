package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.Page;
import it.skillfactory.eco.repository.PageBlockRepository;
import it.skillfactory.eco.repository.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pages")
public class PageAdminController {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageBlockRepository pageBlockRepository;


    // ============================================================
    // DASHBOARD PAGINE
    // ============================================================

    @GetMapping
    public String listPages(Model model) {

        model.addAttribute(
                "pages",
                pageRepository.findAll()
        );

        model.addAttribute(
                "blocks",
                pageBlockRepository.findAllByOrderByPositionAsc()
        );

        return "admin/dashboard";
    }


    // ============================================================
    // NUOVA PAGINA
    // ============================================================

    @GetMapping("/new")
    public String newPage(Model model) {

        Page page = new Page();

        // Larghezza predefinita
        page.setWidthPercent(100);

        model.addAttribute(
                "page",
                page
        );

        return "admin/page-form";
    }


    // ============================================================
    // MODIFICA PAGINA
    // ============================================================

    @GetMapping("/edit/{id}")
    public String editPage(
            @PathVariable Long id,
            Model model) {

        Page page =
                pageRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "ID Pagina non valido: " + id
                                )
                        );

        // Compatibilità con pagine create prima
        // dell'introduzione di widthPercent
        if (page.getWidthPercent() == null) {

            page.setWidthPercent(100);
        }

        model.addAttribute(
                "page",
                page
        );

        return "admin/page-form";
    }


    // ============================================================
    // SALVA PAGINA
    // ============================================================

    @PostMapping("/save")
    public String savePage(
            @ModelAttribute("page") Page page,
            Model model) {

        try {

            // ====================================================
            // CONTROLLO TITOLO
            // ====================================================

            if (page.getTitle() == null ||
                page.getTitle().trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Il titolo della pagina è obbligatorio."
                );

                return "admin/page-form";
            }


            // ====================================================
            // CONTROLLO SLUG
            // ====================================================

            if (page.getSlug() == null ||
                page.getSlug().trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Lo slug della pagina è obbligatorio."
                );

                return "admin/page-form";
            }


            // ====================================================
            // FORMATTAZIONE SLUG
            // ====================================================

            String formattedSlug =
                    page.getSlug()
                        .toLowerCase()
                        .trim()
                        .replaceAll("[^a-z0-9]+", "-")
                        .replaceAll("^-+|-+$", "");

            page.setSlug(formattedSlug);


            // ====================================================
            // CONTROLLO CONTENUTO
            // ====================================================

            if (page.getContentHtml() == null) {

                page.setContentHtml("");
            }


            // ====================================================
            // LARGHEZZA BLOCCO
            // ====================================================

            Integer width =
                    page.getWidthPercent();

            // Se non specificata
            if (width == null) {

                width = 100;
            }

            // Minimo 10%
            if (width < 10) {

                width = 10;
            }

            // Massimo 100%
            if (width > 100) {

                width = 100;
            }

            page.setWidthPercent(width);


            // ====================================================
            // SALVATAGGIO
            // ====================================================

            pageRepository.save(page);


            return "redirect:/admin/pages";


        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    "Errore durante il salvataggio della pagina: "
                    + e.getMessage()
            );

            return "admin/page-form";

        }

    }


    // ============================================================
    // ELIMINA PAGINA
    // ============================================================

    @GetMapping("/delete/{id}")
    public String deletePage(
            @PathVariable Long id) {

        pageRepository.deleteById(id);

        return "redirect:/admin/pages";
    }

}
