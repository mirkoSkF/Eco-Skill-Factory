package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.Page;
import it.skillfactory.eco.repository.PageRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PublicPageController {

    private final PageRepository pageRepository;

    public PublicPageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping("/p/{slug}")
    public String renderPage(
            @PathVariable String slug,
            Model model) {

        System.out.println("=================================");
        System.out.println("RICERCA PAGINA");
        System.out.println("SLUG: " + slug);
        System.out.println("=================================");

        Page page = pageRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pagina non trovata: " + slug
                        )
                );

        System.out.println("Pagina trovata:");
        System.out.println("ID: " + page.getId());
        System.out.println("Titolo: " + page.getTitle());
        System.out.println("Slug: " + page.getSlug());

        model.addAttribute("page", page);

        return "pagina";
    }
}
