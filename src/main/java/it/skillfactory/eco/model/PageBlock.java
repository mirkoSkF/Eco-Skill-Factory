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

    // ============================================================
    // BORDO RIGA / BLOCCO
    // ============================================================
    // NONE, THIN_SOLID, MEDIUM_SOLID, THICK_SOLID, DASHED, DOTTED
    @Column(name = "border_style")
    private String borderStyle = "NONE";

    @Column(name = "border_color")
    private String borderColor = "#334155"; // Default dark/slate

    // ============================================================
    // PERSONALIZZAZIONI GRAFICHE CARD E CAROSELLO
    // ============================================================
    // Stile spigoli Card: ROUNDED, SQUARED, TOP_ROUNDED
    @Column(name = "card_radius_style")
    private String cardRadiusStyle = "ROUNDED";

    // Stile spigoli Carosello Standard: ROUNDED, SQUARED
    @Column(name = "carousel_radius_style")
    private String carouselRadiusStyle = "ROUNDED";

    // Colore di sfondo del pulsante Card / Carosello Standard
    @Column(name = "button_background_color")
    private String buttonBackgroundColor = "#00dc82";

    // Colore del testo del pulsante Card / Carosello Standard
    @Column(name = "button_text_color")
    private String buttonTextColor = "#0f172a";

    // Stile spigoli del pulsante: ROUNDED, SQUARED
    @Column(name = "button_radius_style")
    private String buttonRadiusStyle = "ROUNDED";


    // ============================================================
    // COSTRUTTORI
    // ============================================================
    public PageBlock() {}


    // ============================================================
    // GETTER E SETTER CON FALLBACK
    // ============================================================

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

    public String getBorderStyle() {
        return borderStyle != null && !borderStyle.isBlank() ? borderStyle : "NONE";
    }

    public void setBorderStyle(String borderStyle) {
        this.borderStyle = borderStyle;
    }

    public String getBorderColor() {
        return borderColor != null && !borderColor.isBlank() ? borderColor : "#334155";
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getCardRadiusStyle() {
        return cardRadiusStyle != null && !cardRadiusStyle.isBlank() ? cardRadiusStyle : "ROUNDED";
    }

    public void setCardRadiusStyle(String cardRadiusStyle) {
        this.cardRadiusStyle = cardRadiusStyle;
    }

    public String getCarouselRadiusStyle() {
        return carouselRadiusStyle != null && !carouselRadiusStyle.isBlank() ? carouselRadiusStyle : "ROUNDED";
    }

    public void setCarouselRadiusStyle(String carouselRadiusStyle) {
        this.carouselRadiusStyle = carouselRadiusStyle;
    }

    public String getButtonBackgroundColor() {
        return buttonBackgroundColor != null && !buttonBackgroundColor.isBlank()
                ? buttonBackgroundColor
                : "#00dc82";
    }

    public void setButtonBackgroundColor(String buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
    }

    public String getButtonTextColor() {
        return buttonTextColor != null && !buttonTextColor.isBlank()
                ? buttonTextColor
                : "#0f172a";
    }

    public void setButtonTextColor(String buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    public String getButtonRadiusStyle() {
        return buttonRadiusStyle != null && !buttonRadiusStyle.isBlank()
                ? buttonRadiusStyle
                : "ROUNDED";
    }

    public void setButtonRadiusStyle(String buttonRadiusStyle) {
        this.buttonRadiusStyle = buttonRadiusStyle;
    }

    // ============================================================
    // METODI HELPER PER IL RENDERING CSS IN THYMELEAF
    // ============================================================

    public String getBorderCssValue() {
        if (this.borderStyle == null || "NONE".equalsIgnoreCase(this.borderStyle)) {
            return "none";
        }
        String color = getBorderColor();
        return switch (this.borderStyle) {
            case "THIN_SOLID"   -> "1px solid " + color;
            case "MEDIUM_SOLID" -> "2px solid " + color;
            case "THICK_SOLID"  -> "4px solid " + color;
            case "DASHED"       -> "2px dashed " + color;
            case "DOTTED"       -> "2px dotted " + color;
            default             -> "none";
        };
    }

    public String getButtonRadiusCssValue() {
        return "SQUARED".equalsIgnoreCase(this.buttonRadiusStyle) ? "0" : "999px";
    }

    // ============================================================
    // GETTER E SETTER STANDARD
    // ============================================================

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
