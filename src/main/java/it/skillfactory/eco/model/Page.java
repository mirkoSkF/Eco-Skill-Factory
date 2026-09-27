package it.skillfactory.eco.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

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

    @Column(nullable = false)
    private Integer widthPercent = 100;

    @Column(nullable = false)
    private Boolean hasForm = false;

    private String formType = "NONE";
    private String courseType;
    private String courseCode;
    private String courseName;
    private String recipientEmail;

    @Column(name = "background_color", length = 50)
    private String backgroundColor;

    @Column(name = "title_color", length = 50)
    private String titleColor;

    @Column(name = "form_bg_color", length = 50)
    private String formBgColor;

    @Lob
    @Column(name = "custom_css", columnDefinition = "LONGTEXT")
    private String customCss;

    private String formInputBgColor;
    private String formTextColor;
    private String formPlaceholderColor;
    private String titleFont;

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

    public Boolean getHasForm() {
        return hasForm != null && hasForm;
    }

    public void setHasForm(Boolean hasForm) {
        this.hasForm = hasForm != null && hasForm;
        if (!this.hasForm) {
            this.formType = "NONE";
        } else if (this.formType == null || this.formType.trim().isEmpty() || "NONE".equalsIgnoreCase(this.formType)) {
            this.formType = "BOOKING";
        }
    }

    public String getFormType() {
        if (formType == null || formType.trim().isEmpty()) {
            return Boolean.TRUE.equals(hasForm) ? "BOOKING" : "NONE";
        }
        return formType;
    }

    public void setFormType(String formType) {
        if (formType == null || formType.trim().isEmpty()) {
            this.formType = "NONE";
            this.hasForm = false;
            return;
        }

        this.formType = formType;
        this.hasForm = !"NONE".equalsIgnoreCase(formType);
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getFormBgColor() {
        return formBgColor;
    }

    public void setFormBgColor(String formBgColor) {
        this.formBgColor = formBgColor;
    }

    public String getCustomCss() {
        return customCss;
    }

    public void setCustomCss(String customCss) {
        this.customCss = customCss;
    }

    public String getFormInputBgColor() {
        return formInputBgColor;
    }

    public void setFormInputBgColor(String formInputBgColor) {
        this.formInputBgColor = formInputBgColor;
    }

    public String getFormTextColor() {
        return formTextColor;
    }

    public void setFormTextColor(String formTextColor) {
        this.formTextColor = formTextColor;
    }

    public String getFormPlaceholderColor() {
        return formPlaceholderColor;
    }

    public void setFormPlaceholderColor(String formPlaceholderColor) {
        this.formPlaceholderColor = formPlaceholderColor;
    }

    public String getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }
}
