package it.skillfactory.eco.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nav_items")
public class NavItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    private String url;

    private Integer itemOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private NavItem parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("itemOrder ASC")
    private List<NavItem> children = new ArrayList<>();

    public NavItem() {}

    public boolean isDropdown() {
        return children != null && !children.isEmpty();
    }

    // GETTER E SETTER
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getItemOrder() { return itemOrder; }
    public void setItemOrder(Integer itemOrder) { this.itemOrder = itemOrder; }

    public Page getPage() { return page; }
    public void setPage(Page page) { this.page = page; }

    public NavItem getParent() { return parent; }
    public void setParent(NavItem parent) { this.parent = parent; }

    public List<NavItem> getChildren() { return children; }
    public void setChildren(List<NavItem> children) { this.children = children; }
}