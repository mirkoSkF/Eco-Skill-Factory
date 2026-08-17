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
}