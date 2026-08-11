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

    @Column(nullable = false)
    private String titleAdmin;

    @Column(nullable = false)
    private Integer position = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContainerType containerType = ContainerType.CONTAINER;

    @Column(length = 50)
    private String customHeight = "auto";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BlockType blockType;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    private String backgroundColor = "bg-white";

    @OneToMany(mappedBy = "pageBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("itemOrder ASC")
    private List<BlockItem> items = new ArrayList<>();

    // Costruttore vuoto richiesto da JPA
    public PageBlock() {}

    // Metodi Helper
    public void addItem(BlockItem item) {
        items.add(item);
        item.setPageBlock(this);
    }

    public void removeItem(BlockItem item) {
        items.remove(item);
        item.setPageBlock(null);
    }

    // GETTER E SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitleAdmin() { return titleAdmin; }
    public void setTitleAdmin(String titleAdmin) { this.titleAdmin = titleAdmin; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public ContainerType getContainerType() { return containerType; }
    public void setContainerType(ContainerType containerType) { this.containerType = containerType; }

    public String getCustomHeight() { return customHeight; }
    public void setCustomHeight(String customHeight) { this.customHeight = customHeight; }

    public BlockType getBlockType() { return blockType; }
    public void setBlockType(BlockType blockType) { this.blockType = blockType; }

    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public List<BlockItem> getItems() { return items; }
    public void setItems(List<BlockItem> items) { this.items = items; }
}