package it.skillfactory.eco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_style_settings")
public class AuthStyleSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * =========================================================
     * PERSONALIZZAZIONE PROFILO UTENTE
     * =========================================================
     */

    @Column(length = 20)
    private String profileBackgroundColor = "#080c14";

    @Column(length = 20)
    private String profileFormBackgroundColor = "#0f172a";

    @Column(length = 20)
    private String profileInputBackgroundColor = "#0f172a";

    @Column(length = 20)
    private String profileLabelColor = "#cbd5e1";

    @Column(length = 20)
    private String profilePlaceholderColor = "#94a3b8";


    /*
     * =========================================================
     * PERSONALIZZAZIONE LOGIN UTENTE
     * =========================================================
     */

    @Column(length = 20)
    private String loginBackgroundColor = "#080c14";

    @Column(length = 20)
    private String loginFormBackgroundColor = "#0f172a";

    @Column(length = 20)
    private String loginInputBackgroundColor = "#0f172a";

    @Column(length = 20)
    private String loginLabelColor = "#cbd5e1";

    @Column(length = 20)
    private String loginPlaceholderColor = "#94a3b8";


    /*
     * =========================================================
     * COSTRUTTORE
     * =========================================================
     */

    public AuthStyleSettings() {
    }


    /*
     * =========================================================
     * GETTER E SETTER - PROFILO
     * =========================================================
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public String getProfileFormBackgroundColor() {
        return profileFormBackgroundColor;
    }

    public void setProfileFormBackgroundColor(String profileFormBackgroundColor) {
        this.profileFormBackgroundColor = profileFormBackgroundColor;
    }

    public String getProfileInputBackgroundColor() {
        return profileInputBackgroundColor;
    }

    public void setProfileInputBackgroundColor(String profileInputBackgroundColor) {
        this.profileInputBackgroundColor = profileInputBackgroundColor;
    }

    public String getProfileLabelColor() {
        return profileLabelColor;
    }

    public void setProfileLabelColor(String profileLabelColor) {
        this.profileLabelColor = profileLabelColor;
    }

    public String getProfilePlaceholderColor() {
        return profilePlaceholderColor;
    }

    public void setProfilePlaceholderColor(String profilePlaceholderColor) {
        this.profilePlaceholderColor = profilePlaceholderColor;
    }


    /*
     * =========================================================
     * GETTER E SETTER - LOGIN
     * =========================================================
     */

    public String getLoginBackgroundColor() {
        return loginBackgroundColor;
    }

    public void setLoginBackgroundColor(String loginBackgroundColor) {
        this.loginBackgroundColor = loginBackgroundColor;
    }

    public String getLoginFormBackgroundColor() {
        return loginFormBackgroundColor;
    }

    public void setLoginFormBackgroundColor(String loginFormBackgroundColor) {
        this.loginFormBackgroundColor = loginFormBackgroundColor;
    }

    public String getLoginInputBackgroundColor() {
        return loginInputBackgroundColor;
    }

    public void setLoginInputBackgroundColor(String loginInputBackgroundColor) {
        this.loginInputBackgroundColor = loginInputBackgroundColor;
    }

    public String getLoginLabelColor() {
        return loginLabelColor;
    }

    public void setLoginLabelColor(String loginLabelColor) {
        this.loginLabelColor = loginLabelColor;
    }

    public String getLoginPlaceholderColor() {
        return loginPlaceholderColor;
    }

    public void setLoginPlaceholderColor(String loginPlaceholderColor) {
        this.loginPlaceholderColor = loginPlaceholderColor;
    }
}