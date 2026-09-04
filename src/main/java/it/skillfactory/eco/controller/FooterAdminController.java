package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.FooterConfig;
import it.skillfactory.eco.model.FooterItem;
import it.skillfactory.eco.repository.FooterConfigRepository;
import it.skillfactory.eco.repository.FooterItemRepository;
import it.skillfactory.eco.repository.PageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/footer")
public class FooterAdminController {

    private final FooterItemRepository footerItemRepository;
    private final FooterConfigRepository footerConfigRepository;
    private final PageRepository pageRepository;

    public FooterAdminController(FooterItemRepository footerItemRepository,
                                 FooterConfigRepository footerConfigRepository,
                                 PageRepository pageRepository) {
        this.footerItemRepository = footerItemRepository;
        this.footerConfigRepository = footerConfigRepository;
        this.pageRepository = pageRepository;
    }

    @GetMapping
    public String index(@RequestParam(value = "editId", required = false) Long editId, Model model) {
        FooterItem newItem;
        if (editId != null) {
            newItem = footerItemRepository.findById(editId).orElse(new FooterItem());
        } else {
            newItem = new FooterItem();
        }

        FooterConfig config = footerConfigRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> footerConfigRepository.save(new FooterConfig()));

        model.addAttribute("newItem", newItem);
        model.addAttribute("footerConfig", config);
        model.addAttribute("footerItems", footerItemRepository.findByParentIsNullOrderByItemOrderAsc());
        model.addAttribute("pages", pageRepository.findAll());

        return "admin/footer";
    }

    @PostMapping("/item/save")
    public String saveItem(@ModelAttribute("newItem") FooterItem item) {
        if (item.getPage() != null && item.getPage().getId() == null) {
            item.setPage(null);
        }
        if (item.getParent() != null && item.getParent().getId() == null) {
            item.setParent(null);
        }
        footerItemRepository.save(item);
        return "redirect:/admin/footer";
    }

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        footerItemRepository.deleteById(id);
        return "redirect:/admin/footer";
    }

    @PostMapping("/config/save")
    public String saveConfig(@ModelAttribute("footerConfig") FooterConfig config) {
        footerConfigRepository.save(config);
        return "redirect:/admin/footer";
    }

    @PostMapping("/config/reset")
    public String resetConfig() {
        FooterConfig config = footerConfigRepository.findAll().stream()
                .findFirst()
                .orElseGet(FooterConfig::new);

        config.setBackgroundColor("#0f172a");
        config.setTextColor("#94a3b8");
        config.setLinkColor("#00dc82");
        config.setOpacity(0.95);
        config.setCopyrightText("© 2026 Eco Skill Factory. Tutti i diritti riservati.");

        footerConfigRepository.save(config);
        return "redirect:/admin/footer";
    }
}