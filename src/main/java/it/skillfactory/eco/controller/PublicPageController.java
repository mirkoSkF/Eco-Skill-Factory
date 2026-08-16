package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.Page;
import it.skillfactory.eco.repository.PageRepository;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublicPageController {

    private final PageRepository pageRepository;
    private final JavaMailSender mailSender;

    public PublicPageController(PageRepository pageRepository, JavaMailSender mailSender) {
        this.pageRepository = pageRepository;
        this.mailSender = mailSender;
    }

    // ============================================================
    // VISUALIZZAZIONE PAGINA DINAMICA (GET /p/{slug})
    // ============================================================

    @GetMapping("/p/{slug}")
    public String renderPage(
            @PathVariable String slug,
            Model model) {

        Page page = pageRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pagina non trovata: " + slug
                        )
                );

        model.addAttribute("page", page);
        return "pagina";
    }

    // ============================================================
    // RICEZIONE E INVIO EMAIL FORM (POST /p/prenota)
    // ============================================================

    @PostMapping("/p/prenota")
    public String handleBooking(
            @RequestParam(required = false) String formType,
            @RequestParam(required = false) String pageSlug,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String recipientEmail,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam(required = false) String codiceFiscale,
            @RequestParam(required = false) String citta,
            @RequestParam(required = false) String organizzazione,
            @RequestParam(required = false) String ruolo,
            @RequestParam(required = false) String note,
            @RequestParam(required = false) Boolean privacyCheck,   // 1. Informativa Privacy (Obbligatorio)
            @RequestParam(required = false) Boolean marketingCheck, // 2. Marketing / Promo (Opzionale)
            @RequestParam(value = "thirdPartyCheck", required = false) Boolean profilingCheck, // 3. Mappato sull'attributo name="thirdPartyCheck" dell'HTML
            RedirectAttributes redirectAttributes) {

        String mode = (formType != null && !formType.trim().isEmpty()) ? formType : "BOOKING";

        System.out.println("=================================");
        System.out.println("NUOVA RICHIESTA FORM (" + mode + ")");
        if ("BOOKING".equalsIgnoreCase(mode)) {
            System.out.println("Corso: " + courseName + " (" + courseCode + ")");
        }
        System.out.println("Utente: " + nome + " " + cognome);
        if ("ORG_INFO".equalsIgnoreCase(mode)) {
            System.out.println("Organizzazione: " + organizzazione + " | Ruolo: " + ruolo);
        }
        System.out.println("Email Utente: " + email);
        System.out.println("Destinatario Email: " + recipientEmail);
        System.out.println("=================================");

        // Invio Mail
        if (recipientEmail != null && !recipientEmail.trim().isEmpty()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("selezione@skillfactory.it");
                message.setTo(recipientEmail);

                String subject;
                StringBuilder body = new StringBuilder();

                if ("ORG_INFO".equalsIgnoreCase(mode)) {
                    subject = "Nuova richiesta informazioni aziendale da: " + (organizzazione != null ? organizzazione : nome + " " + cognome);
                    body.append("Hai ricevuto una nuova richiesta informazioni per organizzazioni!\n\n");
                    body.append("Dati Referente e Organizzazione:\n");
                    body.append("- Nome e Cognome: ").append(nome).append(" ").append(cognome).append("\n");
                    body.append("- Email: ").append(email).append("\n");
                    body.append("- Telefono: ").append(telefono).append("\n");
                    body.append("- Organizzazione: ").append(organizzazione != null ? organizzazione : "-").append("\n");
                    body.append("- Ruolo: ").append(ruolo != null ? ruolo : "-").append("\n");
                } else if ("INFO".equalsIgnoreCase(mode)) {
                    subject = "Nuova richiesta informazioni da: " + nome + " " + cognome;
                    body.append("Hai ricevuto una nuova richiesta di informazioni!\n\n");
                    body.append("Dati Utente:\n");
                    body.append("- Nome e Cognome: ").append(nome).append(" ").append(cognome).append("\n");
                    body.append("- Email: ").append(email).append("\n");
                    body.append("- Telefono: ").append(telefono).append("\n");
                } else {
                    subject = "Nuova prenotazione: " + (courseName != null ? courseName : "Corso");
                    body.append("Hai ricevuto una nuova iscrizione al corso!\n\n");
                    body.append("Dettagli Corso:\n");
                    body.append("- Tipologia: ").append(courseType != null ? courseType : "-").append("\n");
                    body.append("- Codice: ").append(courseCode != null ? courseCode : "-").append("\n");
                    body.append("- Nome: ").append(courseName != null ? courseName : "-").append("\n\n");
                    body.append("Dati Candidato:\n");
                    body.append("- Nome: ").append(nome).append(" ").append(cognome).append("\n");
                    body.append("- Email: ").append(email).append("\n");
                    body.append("- Telefono: ").append(telefono).append("\n");
                    body.append("- Codice Fiscale: ").append(codiceFiscale != null ? codiceFiscale : "-").append("\n");
                    body.append("- Città: ").append(citta != null ? citta : "-").append("\n");
                }

                body.append("- Note: ").append(note != null ? note : "-").append("\n\n");
                body.append("Consensi Privacy Espressi:\n");
                body.append("- Informativa Privacy (Obbligatorio): ").append(Boolean.TRUE.equals(privacyCheck) ? "ACCETTATO" : "NON ACCETTATO").append("\n");
                body.append("- Comunicazioni Marketing (Opzionale): ").append(Boolean.TRUE.equals(marketingCheck) ? "ACCONSENTITO" : "NON ACCONSENTITO").append("\n");
                body.append("- Profilazione / Terze Parti (Opzionale): ").append(Boolean.TRUE.equals(profilingCheck) ? "ACCONSENTITO" : "NON ACCONSENTITO").append("\n");

                message.setSubject(subject);
                message.setText(body.toString());
                mailSender.send(message);

                System.out.println("Email inviata con successo a " + recipientEmail);
            } catch (Exception e) {
                System.err.println("Errore durante l'invio dell'email: " + e.getMessage());
            }
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Richiesta inviata con successo! Ti ricontatteremo a breve."
        );

        if (pageSlug != null && !pageSlug.trim().isEmpty()) {
            return "redirect:/p/" + pageSlug;
        }

        return "redirect:/";
    }
}