package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.NavItem;
import it.skillfactory.eco.repository.NavItemRepository;
import it.skillfactory.eco.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/navbar")
public class NavbarAdminController {

    @Autowired
    private NavItemRepository navItemRepository;

    @Autowired
    private PageRepository pageRepository;


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

        // Default: stessa scheda
        newItem.setOpenInNewTab(false);

        model.addAttribute("newItem", newItem);

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
     * Eliminazione voce
     */
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        navItemRepository.deleteById(id);

        return "redirect:/admin/navbar";
    }
}
