package it.skillfactory.eco.controller;

import it.skillfactory.eco.repository.PageBlockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PageBlockRepository blockRepository;

    public HomeController(PageBlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Recupera le righe ordinate per 'position'
        model.addAttribute("blocks", blockRepository.findAllByOrderByPositionAsc());
        return "index";
    }
}