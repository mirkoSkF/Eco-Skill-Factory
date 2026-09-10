package it.skillfactory.eco.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "page_blocks")
public class PageBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleAdmin;

    private Integer position = 1;

    private String customHeight = "auto";

    private Integer widthPercent = 100;

    @Enumerated(EnumType.STRING)
    private BlockType blockType;

    @Enumerated(EnumType.STRING)
    private ContainerType containerType = ContainerType.CONTAINER;

    private String backgroundColor;

    private String imageUrl;

    private String backgroundImageUrl;

    // Regolazioni avanzate per l'immagine di sfondo
    private Integer bgImageOpacity = 100;
    private Integer bgImageSaturation = 100;
    private Integer bgImageGrayscale = 0;
    private Integer bgImageBrightness = 100; // Default 100%

    private boolean draft = true; // Default: Inserita come Bozza

    // ============================================================
    // TIPOLOGIA CAROSELLO (STANDARD / MULTI_IMAGE)
    // ============================================================
    @Column(name = "carousel_type")
    private String carouselType = "STANDARD";

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    @OneToMany(mappedBy = "pageBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("itemOrder ASC")
    private List<BlockItem> items = new ArrayList<>();
    
    // ============================================================
    // PERSONALIZZAZIONE DIMENSIONI CARD E CAROSELLI
    // ============================================================
    // Card per riga (2, 3 o 4 colonne su desktop)
    @Column(name = "cards_per_row")
    private Integer cardsPerRow = 3;

    // Altezza immagine delle card (es. 220px, 280px, 360px)
    @Column(name = "card_image_height")
    private String cardImageHeight = "280px";

    // Altezza slide per Carosello Standard (Hero)
    @Column(name = "carousel_slide_height")
    private String carouselSlideHeight = "500px";

    // Larghezza singola slide per Carosello Multi-Immagine (in px)
    @Column(name = "multi_item_width")
    private Integer multiItemWidth = 390;

    // --- GETTER E SETTER CON FALLBACK ---
    public Integer getCardsPerRow() {
        return cardsPerRow != null && cardsPerRow > 0 ? cardsPerRow : 3;
    }
    public void setCardsPerRow(Integer cardsPerRow) {
        this.cardsPerRow = cardsPerRow;
    }

    public String getCardImageHeight() {
        return cardImageHeight != null && !cardImageHeight.isBlank() ? cardImageHeight : "280px";
    }
    public void setCardImageHeight(String cardImageHeight) {
        this.cardImageHeight = cardImageHeight;
    }

    public String getCarouselSlideHeight() {
        return carouselSlideHeight != null && !carouselSlideHeight.isBlank() ? carouselSlideHeight : "500px";
    }
    public void setCarouselSlideHeight(String carouselSlideHeight) {
        this.carouselSlideHeight = carouselSlideHeight;
    }

    public Integer getMultiItemWidth() {
        return multiItemWidth != null && multiItemWidth > 0 ? multiItemWidth : 390;
    }
    public void setMultiItemWidth(Integer multiItemWidth) {
        this.multiItemWidth = multiItemWidth;
    }


    public PageBlock() {}

    // GETTER E SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitleAdmin() { return titleAdmin; }
    public void setTitleAdmin(String titleAdmin) { this.titleAdmin = titleAdmin; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public String getCustomHeight() { return customHeight; }
    public void setCustomHeight(String customHeight) { this.customHeight = customHeight; }

    public Integer getWidthPercent() { return widthPercent; }
    public void setWidthPercent(Integer widthPercent) { this.widthPercent = widthPercent; }

    public BlockType getBlockType() { return blockType; }
    public void setBlockType(BlockType blockType) { this.blockType = blockType; }

    public ContainerType getContainerType() { return containerType; }
    public void setContainerType(ContainerType containerType) { this.containerType = containerType; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getBackgroundImageUrl() { return backgroundImageUrl; }
    public void setBackgroundImageUrl(String backgroundImageUrl) { this.backgroundImageUrl = backgroundImageUrl; }

    public Integer getBgImageOpacity() { return bgImageOpacity; }
    public void setBgImageOpacity(Integer bgImageOpacity) { this.bgImageOpacity = bgImageOpacity; }

    public Integer getBgImageSaturation() { return bgImageSaturation; }
    public void setBgImageSaturation(Integer bgImageSaturation) { this.bgImageSaturation = bgImageSaturation; }

    public Integer getBgImageGrayscale() { return bgImageGrayscale; }
    public void setBgImageGrayscale(Integer bgImageGrayscale) { this.bgImageGrayscale = bgImageGrayscale; }

    public Integer getBgImageBrightness() { return bgImageBrightness; }
    public void setBgImageBrightness(Integer bgImageBrightness) { this.bgImageBrightness = bgImageBrightness; }

    public boolean isDraft() { return draft; }
    public void setDraft(boolean draft) { this.draft = draft; }

    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }

    public List<BlockItem> getItems() { return items; }
    public void setItems(List<BlockItem> items) { this.items = items; }

     public String getCarouselType() {
        return carouselType != null ? carouselType : "STANDARD";
    }

    public void setCarouselType(String carouselType) {
        this.carouselType = carouselType;
    }
}
