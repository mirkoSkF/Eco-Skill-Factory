package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.NavItem;
import it.skillfactory.eco.repository.NavItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalNavbarAdvice {

    @Autowired
    private NavItemRepository navItemRepository;

    @ModelAttribute("navItems")
    public List<NavItem> populateNavbar() {
        return navItemRepository.findByParentIsNullOrderByItemOrderAsc();
    }
}