package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.FooterConfig;
import it.skillfactory.eco.repository.FooterConfigRepository;
import it.skillfactory.eco.repository.FooterItemRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalLayoutAdvice {

    private final FooterItemRepository footerItemRepository;
    private final FooterConfigRepository footerConfigRepository;

    public GlobalLayoutAdvice(FooterItemRepository footerItemRepository,
                              FooterConfigRepository footerConfigRepository) {
        this.footerItemRepository = footerItemRepository;
        this.footerConfigRepository = footerConfigRepository;
    }

    @ModelAttribute
    public void addFooterAttributes(Model model) {
        model.addAttribute("footerItems", footerItemRepository.findByParentIsNullOrderByItemOrderAsc());
        
        FooterConfig config = footerConfigRepository.findAll().stream()
                .findFirst()
                .orElse(new FooterConfig());
        model.addAttribute("footerConfig", config);
    }
}