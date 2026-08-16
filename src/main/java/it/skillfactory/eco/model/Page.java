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
    // LARGHEZZA BLOCCO (%)
    // ============================================================

    @Column(nullable = false)
    private Integer widthPercent = 100;


    // ============================================================
    // IMPOSTAZIONI FORM
    // ============================================================

    @Column(nullable = false)
    private Boolean hasForm = false;

    private String formType = "NONE"; // NONE, BOOKING, INFO, ORG_INFO

    private String courseType;      // Tipologia (es. Corso di Formazione)

    private String courseCode;      // Codice Corso (es. JAVA-2026-01)

    private String courseName;      // Nome Corso (es. Corso Java Developer)

    private String recipientEmail;  // Email a cui inoltrare le iscrizioni / richieste


    // ============================================================
    // COSTRUTTORI
    // ============================================================

    public Page() {
        this.widthPercent = 100;
        this.hasForm = false;
        this.formType = "NONE";
    }

    public Page(String title, String slug, String contentHtml) {
        this.title = title;
        this.slug = slug;
        this.contentHtml = contentHtml;
        this.widthPercent = 100;
        this.hasForm = false;
        this.formType = "NONE";
    }


    // ============================================================
    // GETTERS E SETTERS BASE
    // ============================================================

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

    public Integer getWidthPercent() {
        return widthPercent;
    }

    public void setWidthPercent(Integer widthPercent) {
        this.widthPercent = widthPercent;
    }


    // ============================================================
    // GETTERS E SETTERS FORM E MODALITÀ
    // ============================================================

    public Boolean getHasForm() {
        if (formType != null && !"NONE".equalsIgnoreCase(formType)) {
            return true;
        }
        return hasForm != null ? hasForm : false;
    }

    public void setHasForm(Boolean hasForm) {
        this.hasForm = hasForm;
        if (Boolean.TRUE.equals(hasForm) && ("NONE".equalsIgnoreCase(this.formType) || this.formType == null)) {
            this.formType = "BOOKING";
        } else if (Boolean.FALSE.equals(hasForm)) {
            this.formType = "NONE";
        }
    }

    public String getFormType() {
        if (formType == null || formType.trim().isEmpty()) {
            return (hasForm != null && hasForm) ? "BOOKING" : "NONE";
        }
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
        this.hasForm = !"NONE".equalsIgnoreCase(formType) && formType != null;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}