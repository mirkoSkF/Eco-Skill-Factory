package it.skillfactory.eco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "block_items")
public class BlockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subtitle;

    private String imageUrl;

    private String buttonText;

    private String buttonUrl;

    private String backgroundColor;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    private Integer itemOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_block_id")
    private PageBlock pageBlock;

    public BlockItem() {}

    // GETTER E SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getButtonText() { return buttonText; }
    public void setButtonText(String buttonText) { this.buttonText = buttonText; }

    public String getButtonUrl() { return buttonUrl; }
    public void setButtonUrl(String buttonUrl) { this.buttonUrl = buttonUrl; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }

    public Integer getItemOrder() { return itemOrder; }
    public void setItemOrder(Integer itemOrder) { this.itemOrder = itemOrder; }

    public PageBlock getPageBlock() { return pageBlock; }
    public void setPageBlock(PageBlock pageBlock) { this.pageBlock = pageBlock; }
}