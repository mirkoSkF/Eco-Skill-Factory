package it.skillfactory.eco.controller;

import it.skillfactory.eco.repository.NavItemRepository; // <--- Importa NavItemRepository
import it.skillfactory.eco.repository.PageBlockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PageBlockRepository blockRepository;
    private final NavItemRepository navItemRepository; // <--- Usa NavItemRepository

    // Injection tramite costruttore
    public HomeController(PageBlockRepository blockRepository, NavItemRepository navItemRepository) {
        this.blockRepository = blockRepository;
        this.navItemRepository = navItemRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        // 1. Recupera i blocchi per la homepage
        model.addAttribute("blocks", blockRepository.findAllByOrderByPositionAsc());

        // 2. Recupera solo i NavItem principali (con parent null) ordinati per itemOrder
        // Grazie alle relazioni JPA su NavItem, 'item.children' verrà popolato automaticamente per i dropdown
        model.addAttribute("navItems", navItemRepository.findByParentIsNullOrderByItemOrderAsc());

        return "index";
    }
}