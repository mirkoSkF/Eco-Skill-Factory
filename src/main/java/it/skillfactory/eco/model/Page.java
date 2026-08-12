package it.skillfactory.eco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String slug;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;

    public Page() {
    }

    public Page(String title, String slug, String contentHtml) {
        this.title = title;
        this.slug = slug;
        this.contentHtml = contentHtml;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }
}
