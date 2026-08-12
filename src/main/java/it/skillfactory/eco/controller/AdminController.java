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

    public AdminController(
            PageBlockRepository blockRepository,
            BlockItemRepository itemRepository) {

        this.blockRepository = blockRepository;
        this.itemRepository = itemRepository;
    }


    // ============================================================
    // DASHBOARD
    // ============================================================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "blocks",
                blockRepository.findAllByOrderByPositionAsc()
        );

        return "admin/dashboard";
    }


    // ============================================================
    // NUOVA RIGA
    // ============================================================

    @GetMapping("/block/new")
    public String newBlockForm(Model model) {

        PageBlock block = new PageBlock();

        model.addAttribute(
                "block",
                block
        );

        model.addAttribute(
                "blockTypes",
                BlockType.values()
        );

        model.addAttribute(
                "containerTypes",
                ContainerType.values()
        );

        return "admin/block-form";
    }


    // ============================================================
    // MODIFICA RIGA
    // ============================================================

    @GetMapping("/block/edit/{id}")
    public String editBlockForm(
            @PathVariable Long id,
            Model model) {

        PageBlock block =
                blockRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "ID Riga non valido: " + id
                                )
                        );


        model.addAttribute(
                "block",
                block
        );

        model.addAttribute(
                "blockTypes",
                BlockType.values()
        );

        model.addAttribute(
                "containerTypes",
                ContainerType.values()
        );


        return "admin/block-form";
    }


    // ============================================================
    // SALVA RIGA
    // ============================================================

    @PostMapping("/block/save")
    public String saveBlock(
            @ModelAttribute PageBlock formBlock,
            @RequestParam(
                    value = "imageFile",
                    required = false
            ) MultipartFile imageFile) {


        PageBlock block;


        // ========================================================
        // RECUPERO L'ENTITÀ ESISTENTE
        // ========================================================

        if (formBlock.getId() != null) {

            block =
                    blockRepository.findById(
                            formBlock.getId()
                    )
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "ID Riga non valido: "
                                            + formBlock.getId()
                            )
                    );

        } else {

            block = new PageBlock();
        }


        // ========================================================
        // CAMPI GENERALI
        // ========================================================

        block.setTitleAdmin(
                formBlock.getTitleAdmin()
        );


        block.setPosition(
                formBlock.getPosition()
        );


        block.setContainerType(
                formBlock.getContainerType()
        );


        // ========================================================
        // ALTEZZA
        //
        // Se vuota -> auto
        // Se valorizzata -> viene mantenuta esattamente
        // ========================================================

        String customHeight =
                formBlock.getCustomHeight();


        if (customHeight == null
                || customHeight.trim().isEmpty()) {

            customHeight = "auto";

        } else {

            customHeight =
                    customHeight.trim();
        }


        block.setCustomHeight(
                customHeight
        );


        // ========================================================
        // LARGHEZZA
        // ========================================================

        Integer width =
                formBlock.getWidthPercent();


        if (width == null) {

            width = 100;
        }


        if (width < 10) {

            width = 10;
        }


        if (width > 100) {

            width = 100;
        }


        block.setWidthPercent(
                width
        );


        // ========================================================
        // MODELLO
        // ========================================================

        block.setBlockType(
                formBlock.getBlockType()
        );


        // ========================================================
        // CONTENUTO HTML
        //
        // Per CARDS e CAROUSEL il contenuto principale
        // non viene utilizzato dal nuovo editor.
        //
        // Lo manteniamo comunque nell'entità per non alterare
        // eventuali dati già presenti.
        // ========================================================

        block.setContentHtml(
                formBlock.getContentHtml()
        );


        // ========================================================
        // SFONDO
        // ========================================================

        if (formBlock.getBackgroundColor() != null) {

            block.setBackgroundColor(
                    formBlock.getBackgroundColor()
            );
        }


        // ========================================================
        // IMMAGINE PRINCIPALE
        //
        // CONSENTITA SOLO:
        //
        // JUMBO_DEMO_1
        // JUMBO_DEMO_2
        //
        // NON CONSENTITA:
        //
        // JUMBOTRON
        // JUMBO_2_COL
        // CARDS
        // CAROUSEL
        // ========================================================

        boolean imageAllowed =
                block.getBlockType()
                        == BlockType.JUMBO_DEMO_1

                ||

                block.getBlockType()
                        == BlockType.JUMBO_DEMO_2;


        if (imageAllowed) {


            if (imageFile != null
                    && !imageFile.isEmpty()) {


                String imageUrl =
                        saveUploadedFile(
                                imageFile
                        );


                if (imageUrl != null) {

                    block.setImageUrl(
                            imageUrl
                    );
                }
            }


        } else {


            // Per tutti gli altri modelli
            // l'immagine principale deve essere assente.

            block.setImageUrl(null);
        }


        // ========================================================
        // TESTI DEMO PREDEFINITI
        //
        // SOLO SE IL CONTENUTO È VUOTO
        // ========================================================

        if (block.getContentHtml() == null
                || block.getContentHtml()
                       .trim()
                       .isEmpty()) {


            // ====================================================
            // DEMO 1
            // ====================================================

            if (block.getBlockType()
                    == BlockType.JUMBO_DEMO_1) {


                block.setContentHtml(

                        "<h2>Chi Siamo</h2>" +

                        "<p><strong>Skill Factory</strong> è la " +
                        "<strong>Learning Company</strong> specializzata in " +
                        "<strong>servizi e prodotti per la formazione</strong>. " +
                        "Nasce nel <strong>2011</strong>, con l'obiettivo di " +
                        "<strong>valorizzare</strong> le risorse umane attraverso " +
                        "la riduzione dello <strong>skill shortage</strong>, " +
                        "la carenza di competenze che si crea nel " +
                        "<strong>mercato del lavoro</strong> a causa dei continui " +
                        "cambiamenti dovuti alla " +
                        "<strong>trasformazione digitale</strong>." +
                        "</p>" +

                        "<p>La nostra <strong>Mission</strong> è quella di ridurre " +
                        "il <strong>mismatch</strong> tra " +
                        "<strong>domanda e offerta</strong> di lavoro. " +
                        "Progettiamo ed eroghiamo " +
                        "<strong>corsi di formazione</strong> per creare le " +
                        "<strong>figure professionali</strong> più richieste dalle " +
                        "aziende, individuate attraverso l'" +
                        "<strong>analisi continua dei fabbisogni formativi</strong> " +
                        "del <strong>mercato del lavoro</strong>. " +
                        "Ogni anno eroghiamo oltre " +
                        "<strong>5000 ore di formazione</strong> e " +
                        "<strong>7000 ore di laboratorio</strong>, sia in modalità " +
                        "<strong>sincrona</strong>, sia " +
                        "<strong>asincrona</strong>." +
                        "</p>"
                );
            }


            // ====================================================
            // DEMO 2
            // ====================================================

            else if (block.getBlockType()
                    == BlockType.JUMBO_DEMO_2) {


                block.setContentHtml(

                        "<h2>La Sede</h2>" +

                        "<p>Siamo a <strong>Napoli</strong>, presso il " +
                        "<strong>Centro Direzionale</strong>, all'isola " +
                        "<strong>E2</strong>, al primo piano della " +
                        "<strong>scala A</strong>. La nostra sede si trova a " +
                        "pochi passi dalla stazione di " +
                        "<strong>Piazza Garibaldi</strong> ed è facilmente " +
                        "raggiungibile con tutti i " +
                        "<strong>mezzi pubblici</strong>. Inoltre, per chi è " +
                        "automunito, in zona ci sono " +
                        "<strong>ampi parcheggi</strong>." +
                        "</p>" +

                        "<p>La struttura, di oltre " +
                        "<strong>300 metri quadrati</strong>, dispone di " +
                        "<strong>tre aule attrezzate</strong> con computer e " +
                        "collegamento ad internet, " +
                        "<strong>un laboratorio</strong>, " +
                        "<strong>due aree amministrative</strong> e un'ampia " +
                        "<strong>sala ricreativa</strong>." +
                        "</p>" +

                        "<p>L'ambiente è <strong>accogliente</strong>, con " +
                        "<strong>aria condizionata</strong>, dispone di " +
                        "<strong>servizi</strong> per i diversamente abili e " +
                        "non sono presenti barriere architettoniche." +
                        "</p>"
                );
            }


            // ====================================================
            // JUMBO 2 COLONNE
            // ====================================================

            else if (block.getBlockType()
                    == BlockType.JUMBO_2_COL) {


                block.setContentHtml(

                        "<div class='row g-4'>" +

                        "<div class='col-md-6'>" +

                        "<h2>L' Academy</h2>" +

                        "<p>Attraverso la nostra " +
                        "<strong>Academy delle Professioni Digitali</strong> " +
                        "eroghiamo <strong>percorsi di formazione " +
                        "specialistici</strong> per formare i profili tecnici " +
                        "più richiesti dalle <strong>aziende</strong> che " +
                        "operano nel <strong>mondo digitale</strong>. " +
                        "In 10 anni di attività abbiamo " +
                        "<strong>formato e inserito</strong> nel " +
                        "<strong>mercato</strong> del lavoro oltre " +
                        "<strong>3000 giovani</strong>." +
                        "</p>" +

                        "<p>L'<strong>Academy Skill Factory</strong> nasce " +
                        "con un <strong>duplice obiettivo</strong>:</p>" +

                        "<p>1. <strong>formare</strong> le nuove " +
                        "<strong>figure professionali</strong> del mondo digital;</p>" +

                        "<p>2. <strong>supportare l'Upskilling e il " +
                        "Reskilling</strong> dei professionisti del settore.</p>" +

                        "<p>Offriamo una <strong>formazione di qualità</strong>, " +
                        "mettendo i <strong>nostri studenti</strong> al centro " +
                        "del processo di <strong>apprendimento</strong>, " +
                        "favorendo lo sviluppo delle " +
                        "<strong>competenze</strong> necessarie per la loro " +
                        "<strong>crescita professionale</strong>, attraverso " +
                        "l'acquisizione delle <strong>soft skills</strong> e " +
                        "delle <strong>hard skills</strong> richieste." +
                        "</p>" +

                        "</div>" +

                        "<div class='col-md-6'>" +

                        "<h2>L' Offerta Formativa</h2>" +

                        "<p>I nostri corsi di " +
                        "<strong>specializzazione</strong> possono essere " +
                        "seguiti in <strong>presenza</strong> o a " +
                        "<strong>distanza</strong>.</p>" +

                        "<p>Formiamo i " +
                        "<strong>professionisti</strong> dell'" +
                        "<strong>innovazione</strong> più richiesti dalle " +
                        "<strong>aziende</strong> del " +
                        "<strong>mondo digitale</strong>:</p>" +

                        "<ul class='list-unstyled fw-bold mt-4'>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>SAP CONSULTANT</li>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>SALESFORCE CONSULTANT</li>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>BIG DATA ANALYST</li>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>CYBER SECURITY EXPERT</li>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>WEB / MOBILE DEVELOPER</li>" +

                        "<li class='border-bottom border-secondary " +
                        "border-opacity-25 py-2'>UI / UX DESIGNER</li>" +

                        "</ul>" +

                        "</div>" +

                        "</div>"
                );
            }
        }


        // ========================================================
        // SALVATAGGIO
        //
        // IMPORTANTE:
        // NON TORNIAMO PIÙ ALLA DASHBOARD.
        //
        // Dopo il salvataggio torniamo alla pagina di modifica
        // della stessa riga.
        //
        // Questo permette a CARDS e CAROUSEL di mostrare
        // immediatamente il form per aggiungere il primo elemento.
        // ========================================================

        blockRepository.save(block);


        return "redirect:/admin/block/edit/" + block.getId();
    }


    // ============================================================
    // UPLOAD FILE
    // ============================================================

    private String saveUploadedFile(
            MultipartFile file) {

        try {


            Path uploadPath =
                    Paths.get(UPLOAD_DIR);


            if (!Files.exists(uploadPath)) {

                Files.createDirectories(
                        uploadPath
                );
            }


            String originalFilename =
                    file.getOriginalFilename();


            String cleanFileName =
                    originalFilename != null
                            ? originalFilename
                                .replaceAll("\\s+", "_")
                            : "file";


            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + cleanFileName;


            Path filePath =
                    uploadPath.resolve(
                            fileName
                    );


            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );


            return "/uploads/" + fileName;


        } catch (IOException e) {

            e.printStackTrace();

            return null;
        }
    }


    // ============================================================
    // ELIMINA RIGA
    // ============================================================

    @GetMapping("/block/delete/{id}")
    public String deleteBlock(
            @PathVariable Long id) {


        blockRepository.deleteById(
                id
        );


        return "redirect:/admin/dashboard";
    }


    // ============================================================
    // AGGIUNGI CARD / SLIDE
    // ============================================================

    @PostMapping("/block/{id}/item/add")
    public String addItemToBlock(
            @PathVariable("id") Long id,

            @RequestParam("title")
            String title,

            @RequestParam(
                    value = "itemImageFile",
                    required = false
            )
            MultipartFile imageFile,

            @RequestParam(
                    value = "contentHtml",
                    required = false
            )
            String contentHtml,

            @RequestParam(
                    value = "buttonText",
                    required = false
            )
            String buttonText,

            @RequestParam(
                    value = "buttonUrl",
                    required = false
            )
            String buttonUrl) {


        // ========================================================
        // RECUPERA LA RIGA
        // ========================================================

        PageBlock block =
                blockRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "ID Riga non valido: " + id
                                )
                        );


        // ========================================================
        // CREA ELEMENTO
        // ========================================================

        BlockItem item =
                new BlockItem();


        item.setTitle(
                title
        );


        item.setContentHtml(
                contentHtml
        );


        item.setButtonText(
                buttonText
        );


        item.setButtonUrl(
                buttonUrl
        );


        item.setPageBlock(
                block
        );


        // ========================================================
        // IMMAGINE CARD / SLIDE
        // ========================================================

        if (imageFile != null
                && !imageFile.isEmpty()) {


            String imageUrl =
                    saveUploadedFile(
                            imageFile
                    );


            if (imageUrl != null) {

                item.setImageUrl(
                        imageUrl
                );
            }
        }


        // ========================================================
        // SALVA ELEMENTO
        // ========================================================

        itemRepository.save(
                item
        );


        // ========================================================
        // TORNA ALLA STESSA PAGINA
        //
        // In questo modo:
        //
        // 1. viene mostrata la Card/Slide appena inserita
        // 2. rimane disponibile il form per aggiungerne un'altra
        // ========================================================

        return "redirect:/admin/block/edit/" + id;
    }


    // ============================================================
    // ELIMINA CARD / SLIDE
    // ============================================================

    @GetMapping("/block/{blockId}/item/delete/{itemId}")
    public String deleteItem(
            @PathVariable Long blockId,
            @PathVariable Long itemId) {


        itemRepository.deleteById(
                itemId
        );


        // Dopo la cancellazione torniamo alla stessa pagina.

        return "redirect:/admin/block/edit/" + blockId;
    }

}
