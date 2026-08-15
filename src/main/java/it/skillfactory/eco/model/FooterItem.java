package it.skillfactory.eco.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "footer_items")
public class FooterItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FooterItem parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("itemOrder ASC")
    private List<FooterItem> children = new ArrayList<>();

    @Column(name = "item_order", nullable = false)
    private Integer itemOrder = 0;

    @Column(name = "open_in_new_tab")
    private Boolean openInNewTab = false;

    @Column(name = "column_position")
    private String columnPosition = "SX"; // SX, CENTRO, DX

    public FooterItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Page getPage() { return page; }
    public void setPage(Page page) { this.page = page; }

    public FooterItem getParent() { return parent; }
    public void setParent(FooterItem parent) { this.parent = parent; }

    public List<FooterItem> getChildren() { return children; }
    public void setChildren(List<FooterItem> children) { this.children = children; }

    public Integer getItemOrder() { return itemOrder; }
    public void setItemOrder(Integer itemOrder) { this.itemOrder = itemOrder; }

    public Boolean getOpenInNewTab() { return openInNewTab; }
    public void setOpenInNewTab(Boolean openInNewTab) { this.openInNewTab = openInNewTab; }

    public String getColumnPosition() { return columnPosition; }
    public void setColumnPosition(String columnPosition) { this.columnPosition = columnPosition; }
}