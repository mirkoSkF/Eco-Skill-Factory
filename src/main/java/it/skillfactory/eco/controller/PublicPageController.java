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
    // RICEZIONE E INVIO EMAIL PRENOTAZIONE CORSO (POST /p/prenota)
    // ============================================================

    @PostMapping("/p/prenota")
    public String handleBooking(
            @RequestParam(required = false) String pageSlug,
            @RequestParam(required = false) String courseType,
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String recipientEmail,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String codiceFiscale,
            @RequestParam(required = false) String citta,
            @RequestParam(required = false) String note,
            @RequestParam(required = false) Boolean privacyCheck,
            @RequestParam(required = false) Boolean marketingCheck,
            RedirectAttributes redirectAttributes) {

        System.out.println("=================================");
        System.out.println("NUOVA RICHIESTA DI PRENOTAZIONE");
        System.out.println("Corso: " + courseName + " (" + courseCode + ")");
        System.out.println("Candidato: " + nome + " " + cognome);
        System.out.println("Email Candidato: " + email);
        System.out.println("Destinatario Email: " + recipientEmail);
        System.out.println("=================================");

        // Invio Mail
        if (recipientEmail != null && !recipientEmail.trim().isEmpty()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(recipientEmail);
                message.setSubject("Nuova prenotazione: " + courseName);
                
                String testoEmail = String.format(
                    "Hai ricevuto una nuova iscrizione al corso!\n\n" +
                    "Dettagli Corso:\n" +
                    "- Tipologia: %s\n" +
                    "- Codice: %s\n" +
                    "- Nome: %s\n\n" +
                    "Dati Candidato:\n" +
                    "- Nome: %s %s\n" +
                    "- Email: %s\n" +
                    "- Telefono: %s\n" +
                    "- Codice Fiscale: %s\n" +
                    "- Città: %s\n" +
                    "- Note: %s\n",
                    courseType, courseCode, courseName,
                    nome, cognome, email, telefono, codiceFiscale,
                    (citta != null ? citta : "-"),
                    (note != null ? note : "-")
                );

                message.setText(testoEmail);
                mailSender.send(message);

                System.out.println("Email inviata con successo a " + recipientEmail);
            } catch (Exception e) {
                System.err.println("Errore durante l'invio dell'email: " + e.getMessage());
            }
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Richiesta di prenotazione inviata con successo! Ti ricontatteremo a breve."
        );

        if (pageSlug != null && !pageSlug.trim().isEmpty()) {
            return "redirect:/p/" + pageSlug;
        }

        return "redirect:/";
    }
}