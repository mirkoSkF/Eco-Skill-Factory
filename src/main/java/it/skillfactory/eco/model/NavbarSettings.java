package it.skillfactory.eco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "navbar_settings")
public class NavbarSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "favicon_url")
    private String faviconUrl;

    @Column(name = "opacity")
    private Integer opacity = 90;

    @Column(name = "bg_color")
    private String bgColor = "#0f172a";

    @Column(name = "text_color")
    private String textColor = "#f1f5f9";

    @Column(name = "selected_text_color")
    private String selectedTextColor = "#00dc82";

    @Column(name = "dropdown_bg_color")
    private String dropdownBgColor = "#1e293b";

    @Column(name = "dropdown_text_color")
    private String dropdownTextColor = "#f1f5f9";

    @Column(name = "dropdown_hover_bg_color")
    private String dropdownHoverBgColor = "#00dc82";

    @Column(name = "dropdown_selected_text_color")
    private String dropdownSelectedTextColor = "#0f172a";

    @Column(name = "hide_on_scroll_behavior")
    private String hideOnScrollBehavior = "NONE";

    // Aggiungere all'entità delle impostazioni esistente (es. NavbarSettings):
private boolean userLoginEnabled = false; // Default: disabilitato

public boolean isUserLoginEnabled() { return userLoginEnabled; }
public void setUserLoginEnabled(boolean userLoginEnabled) { this.userLoginEnabled = userLoginEnabled; }

    public NavbarSettings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public Integer getOpacity() {
        return opacity;
    }

    public void setOpacity(Integer opacity) {
        this.opacity = opacity;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(String selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public String getDropdownBgColor() {
        return dropdownBgColor;
    }

    public void setDropdownBgColor(String dropdownBgColor) {
        this.dropdownBgColor = dropdownBgColor;
    }

    public String getDropdownTextColor() {
        return dropdownTextColor;
    }

    public void setDropdownTextColor(String dropdownTextColor) {
        this.dropdownTextColor = dropdownTextColor;
    }

    public String getDropdownHoverBgColor() {
        return dropdownHoverBgColor;
    }

    public void setDropdownHoverBgColor(String dropdownHoverBgColor) {
        this.dropdownHoverBgColor = dropdownHoverBgColor;
    }

    public String getDropdownSelectedTextColor() {
        return dropdownSelectedTextColor;
    }

    public void setDropdownSelectedTextColor(String dropdownSelectedTextColor) {
        this.dropdownSelectedTextColor = dropdownSelectedTextColor;
    }

    public String getHideOnScrollBehavior() {
        return hideOnScrollBehavior;
    }

    public void setHideOnScrollBehavior(String hideOnScrollBehavior) {
        this.hideOnScrollBehavior = hideOnScrollBehavior;
    }
}