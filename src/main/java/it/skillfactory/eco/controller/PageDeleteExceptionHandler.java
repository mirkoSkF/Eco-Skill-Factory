package it.skillfactory.eco.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class PageDeleteExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(
            DataIntegrityViolationException ex, 
            RedirectAttributes redirectAttributes) {

        // Imposta il messaggio di errore che verrà mostrato nel Modal/Popup
        redirectAttributes.addFlashAttribute(
                "errorMessage", 
                "Impossibile eliminare la pagina: è attualmente collegata a una voce della Navbar. Svincola la pagina dalla Navbar prima di eliminarla."
        );

        return "redirect:/admin/pages";
    }
}