package it.skillfactory.eco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // ============================================================
    // TITOLO
    // ============================================================

    @Column(nullable = false)
    private String title;


    // ============================================================
    // SLUG
    // ============================================================

    @Column(unique = true, nullable = false)
    private String slug;


    // ============================================================
    // CONTENUTO HTML
    // ============================================================

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;


    // ============================================================
    // LARGHEZZA BLOCCO
    // ============================================================
    //
    // Valore espresso in percentuale.
    //
    // Esempi:
    // 100 = larghezza completa
    // 90  = 90%
    // 75  = 75%
    // 50  = 50%
    //
    // Default: 100%
    // ============================================================

    @Column(nullable = false)
    private Integer widthPercent = 100;


    // ============================================================
    // COSTRUTTORE VUOTO
    // ============================================================

    public Page() {
        this.widthPercent = 100;
    }


    // ============================================================
    // COSTRUTTORE COMPLETO
    // ============================================================

    public Page(
            String title,
            String slug,
            String contentHtml) {

        this.title = title;
        this.slug = slug;
        this.contentHtml = contentHtml;
        this.widthPercent = 100;
    }


    // ============================================================
    // GET ID
    // ============================================================

    public Long getId() {

        return id;
    }


    // ============================================================
    // SET ID
    // ============================================================

    public void setId(Long id) {

        this.id = id;
    }


    // ============================================================
    // GET TITOLO
    // ============================================================

    public String getTitle() {

        return title;
    }


    // ============================================================
    // SET TITOLO
    // ============================================================

    public void setTitle(String title) {

        this.title = title;
    }


    // ============================================================
    // GET SLUG
    // ============================================================

    public String getSlug() {

        return slug;
    }


    // ============================================================
    // SET SLUG
    // ============================================================

    public void setSlug(String slug) {

        this.slug = slug;
    }


    // ============================================================
    // GET CONTENUTO HTML
    // ============================================================

    public String getContentHtml() {

        return contentHtml;
    }


    // ============================================================
    // SET CONTENUTO HTML
    // ============================================================

    public void setContentHtml(String contentHtml) {

        this.contentHtml = contentHtml;
    }


    // ============================================================
    // GET LARGHEZZA
    // ============================================================

    public Integer getWidthPercent() {

        return widthPercent;
    }


    // ============================================================
    // SET LARGHEZZA
    // ============================================================

    public void setWidthPercent(Integer widthPercent) {

        this.widthPercent = widthPercent;
    }

}
