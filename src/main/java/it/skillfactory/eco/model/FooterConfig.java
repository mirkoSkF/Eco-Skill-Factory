package it.skillfactory.eco.model;

import jakarta.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "footer_config")
public class FooterConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "background_color")
    private String backgroundColor = "#0f172a";

    @Column(name = "opacity")
    private Double opacity = 0.95;

    @Column(name = "copyright_text")
    private String copyrightText = "© 2026 Eco Skill Factory. Tutti i diritti riservati.";

    @Column(name = "vat_number")
    private String vatNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    public FooterConfig() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public Double getOpacity() { return opacity; }
    public void setOpacity(Double opacity) { this.opacity = opacity; }

    public String getCopyrightText() { return copyrightText; }
    public void setCopyrightText(String copyrightText) { this.copyrightText = copyrightText; }

    public String getVatNumber() { return vatNumber; }
    public void setVatNumber(String vatNumber) { this.vatNumber = vatNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getFacebookUrl() { return facebookUrl; }
    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getInstagramUrl() { return instagramUrl; }
    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }

    public String getRgbaStyle() {
        double safeOpacity = (opacity != null) ? opacity : 0.95;
        if (backgroundColor == null || !backgroundColor.startsWith("#") || backgroundColor.length() != 7) {
            return String.format(Locale.US, "rgba(15, 23, 42, %.2f)", safeOpacity);
        }
        try {
            int r = Integer.parseInt(backgroundColor.substring(1, 3), 16);
            int g = Integer.parseInt(backgroundColor.substring(3, 5), 16);
            int b = Integer.parseInt(backgroundColor.substring(5, 7), 16);
            return String.format(Locale.US, "rgba(%d, %d, %d, %.2f)", r, g, b, safeOpacity);
        } catch (Exception e) {
            return String.format(Locale.US, "rgba(15, 23, 42, %.2f)", safeOpacity);
        }
    }
}