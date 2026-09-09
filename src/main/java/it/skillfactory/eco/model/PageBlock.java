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

    private boolean draft = true; // Default: Inserita come Bozza

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    @OneToMany(mappedBy = "pageBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("itemOrder ASC")
    private List<BlockItem> items = new ArrayList<>();

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

    public boolean isDraft() { return draft; }
    public void setDraft(boolean draft) { this.draft = draft; }

    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }

    public List<BlockItem> getItems() { return items; }
    public void setItems(List<BlockItem> items) { this.items = items; }
}
