package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.BlockItem;
import it.skillfactory.eco.model.BlockType;
import it.skillfactory.eco.model.ContainerType;
import it.skillfactory.eco.model.PageBlock;
import it.skillfactory.eco.repository.BlockItemRepository;
import it.skillfactory.eco.repository.PageBlockRepository;
import it.skillfactory.eco.repository.PageRepository;

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
    private final PageRepository pageRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public AdminController(
            PageBlockRepository blockRepository,
            BlockItemRepository itemRepository,
            PageRepository pageRepository) {

        this.blockRepository = blockRepository;
        this.itemRepository = itemRepository;
        this.pageRepository = pageRepository;
    }

    // ============================================================
    // DASHBOARD
    // ============================================================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("blocks", blockRepository.findAllByOrderByPositionAsc());
        model.addAttribute("pages", pageRepository.findAll());
        return "admin/dashboard";
    }

    // ============================================================
    // NUOVA RIGA
    // ============================================================

    @GetMapping("/block/new")
    public String newBlockForm(Model model) {
        PageBlock block = new PageBlock();
        
        model.addAttribute("block", block);
        model.addAttribute("blockTypes", BlockType.values());
        model.addAttribute("containerTypes", ContainerType.values());

        return "admin/block-form";
    }

    // ============================================================
    // MODIFICA RIGA
    // ============================================================

    @GetMapping("/block/edit/{id}")
    public String editBlockForm(@PathVariable Long id, Model model) {
        PageBlock block = blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + id));

        model.addAttribute("block", block);
        model.addAttribute("blockTypes", BlockType.values());
        model.addAttribute("containerTypes", ContainerType.values());

        return "admin/block-form";
    }

    // ============================================================
    // SALVA RIGA
    // ============================================================

    @PostMapping("/block/save")
    public String saveBlock(
            @ModelAttribute("block") PageBlock formBlock,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        PageBlock block;

        if (formBlock.getId() != null) {
            block = blockRepository.findById(formBlock.getId())
                    .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + formBlock.getId()));
        } else {
            block = new PageBlock();
        }

        block.setTitleAdmin(formBlock.getTitleAdmin());
        block.setPosition(formBlock.getPosition());
        block.setContainerType(formBlock.getContainerType());

        // Altezza
        String customHeight = formBlock.getCustomHeight();
        if (customHeight == null || customHeight.trim().isEmpty()) {
            customHeight = "auto";
        } else {
            customHeight = customHeight.trim();
        }
        block.setCustomHeight(customHeight);

        // Larghezza
        Integer width = formBlock.getWidthPercent();
        if (width == null) width = 100;
        if (width < 10) width = 10;
        if (width > 100) width = 100;
        block.setWidthPercent(width);

        block.setBlockType(formBlock.getBlockType());
        block.setContentHtml(formBlock.getContentHtml());

        if (formBlock.getBackgroundColor() != null) {
            block.setBackgroundColor(formBlock.getBackgroundColor());
        }

        // Immagine Principale
        boolean imageAllowed = block.getBlockType() == BlockType.JUMBO_DEMO_1 || block.getBlockType() == BlockType.JUMBO_DEMO_2;

        if (imageAllowed) {
            if (imageFile != null && !imageFile.isEmpty()) {
                deleteUploadedFile(block.getImageUrl());
                String imageUrl = saveUploadedFile(imageFile);
                if (imageUrl != null) {
                    block.setImageUrl(imageUrl);
                }
            }
        } else {
            block.setImageUrl(null);
        }

        PageBlock savedBlock = blockRepository.save(block);

        return "redirect:/admin/block/edit/" + savedBlock.getId();
    }

    // ============================================================
    // UPLOAD & DELETE FILE
    // ============================================================

    private String saveUploadedFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = file.getOriginalFilename();
            String cleanFileName = originalFilename != null ? originalFilename.replaceAll("\\s+", "_") : "file";
            String fileName = UUID.randomUUID() + "_" + cleanFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteUploadedFile(String relativeUrl) {
        if (relativeUrl == null || !relativeUrl.startsWith("/uploads/")) {
            return;
        }
        try {
            String fileName = relativeUrl.replace("/uploads/", "");
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // ELIMINA RIGA
    // ============================================================

    @GetMapping("/block/delete/{id}")
    public String deleteBlock(@PathVariable Long id) {
        blockRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // ============================================================
    // AGGIUNGI CARD / SLIDE
    // ============================================================

    @PostMapping("/block/{id}/item/add")
    public String addItemToBlock(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "itemImageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "contentHtml", required = false) String contentHtml,
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
            String imageUrl = saveUploadedFile(imageFile);
            if (imageUrl != null) {
                item.setImageUrl(imageUrl);
            }
        }

        itemRepository.save(item);
        return "redirect:/admin/block/edit/" + id;
    }

    // ============================================================
    // FORM EDIT CARD / SLIDE
    // ============================================================

    @GetMapping("/block/{blockId}/item/edit/{itemId}")
    public String editItemForm(
            @PathVariable Long blockId,
            @PathVariable Long itemId,
            Model model) {

        PageBlock block = blockRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("ID Riga non valido: " + blockId));

        BlockItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("ID Elemento non valido: " + itemId));

        model.addAttribute("block", block);
        model.addAttribute("editingItem", item);
        model.addAttribute("blockTypes", BlockType.values());
        model.addAttribute("containerTypes", ContainerType.values());

        return "admin/block-form";
    }

    // ============================================================
    // SALVA MODIFICA CARD / SLIDE
    // ============================================================

    @PostMapping("/block/{blockId}/item/update/{itemId}")
    public String updateItem(
            @PathVariable Long blockId,
            @PathVariable Long itemId,
            @RequestParam("title") String title,
            @RequestParam(value = "itemImageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "contentHtml", required = false) String contentHtml,
            @RequestParam(value = "buttonText", required = false) String buttonText,
            @RequestParam(value = "buttonUrl", required = false) String buttonUrl) {

        BlockItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("ID Elemento non valido: " + itemId));

        item.setTitle(title);
        item.setContentHtml(contentHtml);
        item.setButtonText(buttonText);
        item.setButtonUrl(buttonUrl);

        if (imageFile != null && !imageFile.isEmpty()) {
            deleteUploadedFile(item.getImageUrl());
            String imageUrl = saveUploadedFile(imageFile);
            if (imageUrl != null) {
                item.setImageUrl(imageUrl);
            }
        }

        itemRepository.save(item);
        return "redirect:/admin/block/edit/" + blockId;
    }

    // ============================================================
    // ELIMINA CARD / SLIDE
    // ============================================================

    @GetMapping("/block/{blockId}/item/delete/{itemId}")
    public String deleteItem(
            @PathVariable Long blockId,
            @PathVariable Long itemId) {

        itemRepository.findById(itemId).ifPresent(item -> deleteUploadedFile(item.getImageUrl()));
        itemRepository.deleteById(itemId);

        return "redirect:/admin/block/edit/" + blockId;
    }
}