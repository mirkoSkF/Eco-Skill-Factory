package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.BlockItem;
import it.skillfactory.eco.model.BlockType;
import it.skillfactory.eco.model.ContainerType;
import it.skillfactory.eco.model.PageBlock;
import it.skillfactory.eco.repository.BlockItemRepository;
import it.skillfactory.eco.repository.PageBlockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PageBlockRepository blockRepository;
    private final BlockItemRepository itemRepository;

    public AdminController(PageBlockRepository blockRepository, BlockItemRepository itemRepository) {
        this.blockRepository = blockRepository;
        this.itemRepository = itemRepository;
    }

    // Dashboard: Lista di tutte le righe
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("blocks", blockRepository.findAllByOrderByPositionAsc());
        return "admin/dashboard";
    }

    // Form di creazione nuova riga
    @GetMapping("/block/new")
    public String newBlockForm(Model model) {
        PageBlock block = new PageBlock();
        model.addAttribute("block", block);
        model.addAttribute("blockTypes", BlockType.values());
        model.addAttribute("containerTypes", ContainerType.values());
        return "admin/block-form";
    }

    // Form di modifica riga esistente per ID (con TinyMCE)
    @GetMapping("/block/edit/{id}")
    public String editBlockForm(@PathVariable Long id, Model model) {
        PageBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + id));
        model.addAttribute("block", block);
        model.addAttribute("blockTypes", BlockType.values());
        model.addAttribute("containerTypes", ContainerType.values());
        return "admin/block-form";
    }

    // Salva Configurazione Riga
    @PostMapping("/block/save")
    public String saveBlock(@ModelAttribute PageBlock block) {
        blockRepository.save(block);
        return "redirect:/admin/dashboard";
    }

    // Elimina Riga
    @GetMapping("/block/delete/{id}")
    public String deleteBlock(@PathVariable Long id) {
        blockRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // Aggiungi Card / Slide ad una riga esistente
    @PostMapping("/block/{blockId}/item/add")
    public String addItemToBlock(@PathVariable Long blockId, @ModelAttribute BlockItem item) {
        PageBlock block = blockRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + blockId));
        block.addItem(item);
        blockRepository.save(block);
        return "redirect:/admin/block/edit/" + blockId;
    }

    // Elimina Card / Slide
    @GetMapping("/block/{blockId}/item/delete/{itemId}")
    public String deleteItem(@PathVariable Long blockId, @PathVariable Long itemId) {
        itemRepository.deleteById(itemId);
        return "redirect:/admin/block/edit/" + blockId;
    }
}