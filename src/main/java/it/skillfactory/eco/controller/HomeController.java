package it.skillfactory.eco.controller;

import it.skillfactory.eco.repository.PageBlockRepository;
import it.skillfactory.eco.repository.PageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class HomeController {

    private final PageBlockRepository blockRepository;
    private final PageRepository pageRepository;

    public HomeController(PageBlockRepository blockRepository, PageRepository pageRepository) {
        this.blockRepository = blockRepository;
        this.pageRepository = pageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        // Recupera le righe/blocchi ordinate per 'position'
        model.addAttribute("blocks", blockRepository.findAllByOrderByPositionAsc());

        // Recupera le pagine per eventuale menu di navigazione
        // Se non hai un NavItemRepository dedicato, passa una lista per evitare eccezioni Thymeleaf su navItems
        try {
            model.addAttribute("navItems", pageRepository.findAll());
        } catch (Exception e) {
            model.addAttribute("navItems", Collections.emptyList());
        }

        return "index";
    }
}