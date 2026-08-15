package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.Page;
import it.skillfactory.eco.repository.PageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/pages")
public class PageAdminController {

    @Autowired
    private PageRepository pageRepository;


    // ============================================================
    // TABELLA PAGINE CON RICERCA E PAGINAZIONE
    // ============================================================

    @GetMapping
    public String listPages(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {

        List<Page> allPages = pageRepository.findAll();

        // FILTRAGGIO PER RICERCA (SU SLUG O TITOLO)
        if (search != null && !search.trim().isEmpty()) {
            String term = search.trim().toLowerCase();
            allPages = allPages.stream()
                    .filter(p -> (p.getSlug() != null && p.getSlug().toLowerCase().contains(term))
                              || (p.getTitle() != null && p.getTitle().toLowerCase().contains(term)))
                    .collect(Collectors.toList());
        }

        // CALCOLO PAGINAZIONE
        int totalItems = allPages.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        if (totalPages < 1) {
            totalPages = 1;
        }

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int start = (page - 1) * size;
        int end = Math.min(start + size, totalItems);

        List<Page> paginatedPages = (start <= totalItems && start >= 0)
                ? allPages.subList(start, end)
                : Collections.emptyList();

        model.addAttribute("pages", paginatedPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("pageSize", size);
        model.addAttribute("search", search != null ? search.trim() : "");

        return "admin/tab-pagine";
    }


    // ============================================================
    // NUOVA PAGINA
    // ============================================================

    @GetMapping("/new")
    public String newPage(Model model) {

        Page page = new Page();

        page.setWidthPercent(100);

        page.setContentHtml("");

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


        // Compatibilità con pagine vecchie

        if (page.getWidthPercent() == null) {

            page.setWidthPercent(100);
        }


        if (page.getContentHtml() == null) {

            page.setContentHtml("");
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
            // TITOLO
            // ====================================================

            if (page.getTitle() == null ||
                page.getTitle().trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Il titolo della pagina è obbligatorio."
                );

                preparaFormErrore(model, page);

                return "admin/page-form";
            }


            page.setTitle(
                    page.getTitle().trim()
            );


            // ====================================================
            // SLUG
            // ====================================================

            if (page.getSlug() == null ||
                page.getSlug().trim().isEmpty()) {

                model.addAttribute(
                        "error",
                        "Lo slug della pagina è obbligatorio."
                );

                preparaFormErrore(model, page);

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


            // ====================================================
            // CONTROLLO SLUG
            // ====================================================

            if (formattedSlug.isEmpty()) {

                model.addAttribute(
                        "error",
                        "Lo slug inserito non è valido."
                );

                preparaFormErrore(model, page);

                return "admin/page-form";
            }


            page.setSlug(formattedSlug);


            // ====================================================
            // CONTROLLO SLUG DUPLICATO
            // ====================================================

            boolean slugEsistente;

            if (page.getId() == null) {

                slugEsistente =
                        pageRepository.existsBySlug(
                                page.getSlug()
                        );

            } else {

                slugEsistente =
                        pageRepository.existsBySlugAndIdNot(
                                page.getSlug(),
                                page.getId()
                        );
            }


            if (slugEsistente) {

                model.addAttribute(
                        "error",
                        "Esiste già una pagina con lo slug: "
                        + page.getSlug()
                );

                preparaFormErrore(model, page);

                return "admin/page-form";
            }


            // ====================================================
            // CONTENUTO
            // ====================================================

            if (page.getContentHtml() == null) {

                page.setContentHtml("");
            }


            // ====================================================
            // LARGHEZZA
            // ====================================================

            Integer width =
                    page.getWidthPercent();


            if (width == null) {

                width = 100;
            }


            if (width < 10) {

                width = 10;
            }


            if (width > 100) {

                width = 100;
            }


            page.setWidthPercent(width);


            // ====================================================
            // SALVATAGGIO
            // ====================================================

            Page savedPage =
                    pageRepository.save(page);


            // ====================================================
            // LOG
            // ====================================================

            System.out.println("=================================");
            System.out.println("PAGINA SALVATA");
            System.out.println("ID: " + savedPage.getId());
            System.out.println("TITOLO: " + savedPage.getTitle());
            System.out.println("SLUG: " + savedPage.getSlug());
            System.out.println("WIDTH: " + savedPage.getWidthPercent());
            System.out.println("=================================");


            // ====================================================
            // RITORNO ALLA GESTIONE PAGINE
            // ====================================================

            return "redirect:/admin/pages";


        } catch (Exception e) {

            e.printStackTrace();


            // ====================================================
            // VALORI NECESSARI AL FORM
            // ====================================================

            preparaFormErrore(
                    model,
                    page
            );


            model.addAttribute(
                    "error",
                    "Errore durante il salvataggio della pagina: "
                    + e.getMessage()
            );


            return "admin/page-form";
        }
    }


    // ============================================================
    // PREPARAZIONE FORM IN CASO DI ERRORE
    // ============================================================

    private void preparaFormErrore(Model model, Page page) {

        if (page.getWidthPercent() == null) {
            page.setWidthPercent(100);
        }

        if (page.getContentHtml() == null) {
            page.setContentHtml(""); // Correretto: setContentHtml invece di getContentHtml
        }

        model.addAttribute("page", page);
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