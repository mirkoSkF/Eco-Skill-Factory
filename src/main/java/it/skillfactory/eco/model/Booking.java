package it.skillfactory.eco.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formType;
    private String pageSlug;
    private String courseType;
    private String courseCode;
    private String courseName;

    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String codiceFiscale;
    private String citta;
    private String organizzazione;
    private String ruolo;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String note;

    private Boolean privacyCheck;
    private Boolean marketingCheck;
    private Boolean profilingCheck;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Boolean anonymized = false;

    public Booking() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFormType() { return formType; }
    public void setFormType(String formType) { this.formType = formType; }

    public String getPageSlug() { return pageSlug; }
    public void setPageSlug(String pageSlug) { this.pageSlug = pageSlug; }

    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public String getOrganizzazione() { return organizzazione; }
    public void setOrganizzazione(String organizzazione) { this.organizzazione = organizzazione; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Boolean getPrivacyCheck() { return privacyCheck; }
    public void setPrivacyCheck(Boolean privacyCheck) { this.privacyCheck = privacyCheck; }

    public Boolean getMarketingCheck() { return marketingCheck; }
    public void setMarketingCheck(Boolean marketingCheck) { this.marketingCheck = marketingCheck; }

    public Boolean getProfilingCheck() { return profilingCheck; }
    public void setProfilingCheck(Boolean profilingCheck) { this.profilingCheck = profilingCheck; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getAnonymized() { return anonymized; }
    public void setAnonymized(Boolean anonymized) { this.anonymized = anonymized; }
}