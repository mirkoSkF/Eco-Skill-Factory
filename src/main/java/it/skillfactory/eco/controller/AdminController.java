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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PageBlockRepository blockRepository;
    private final BlockItemRepository itemRepository;

    private static final String UPLOAD_DIR = "uploads/";

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
    @PostMapping("/block/{id}/item/add")
    public String addItemToBlock(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("contentHtml") String contentHtml,
            @RequestParam(value = "buttonText", required = false) String buttonText,
            @RequestParam(value = "buttonUrl", required = false) String buttonUrl) {

        PageBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + id));

        BlockItem item = new BlockItem();
        item.setTitle(title);
        item.setContentHtml(contentHtml);
        item.setButtonText(buttonText);
        item.setButtonUrl(buttonUrl);
        item.setPageBlock(block);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = imageFile.getOriginalFilename();
                String cleanFileName = (originalFilename != null) ? originalFilename.replaceAll("\\s+", "_") : "file";
                String fileName = UUID.randomUUID().toString() + "_" + cleanFileName;

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                item.setImageUrl("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        itemRepository.save(item);

        return "redirect:/admin/block/edit/" + id;
    }

    // Elimina Card / Slide
    @GetMapping("/block/{blockId}/item/delete/{itemId}")
    public String deleteItem(@PathVariable Long blockId, @PathVariable Long itemId) {
        itemRepository.deleteById(itemId);
        return "redirect:/admin/block/edit/" + blockId;
    }
}