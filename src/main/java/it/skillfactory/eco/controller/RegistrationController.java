package it.skillfactory.eco.controller;

import it.skillfactory.eco.dto.RegistrationRequest;
import it.skillfactory.eco.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        if (!model.containsAttribute("registrationRequest")) {
            model.addAttribute(
                    "registrationRequest",
                    new RegistrationRequest()
            );
        }

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registrationRequest")
            RegistrationRequest registrationRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!registrationRequest.getPassword()
                .equals(registrationRequest.getConfirmPassword())) {

            bindingResult.rejectValue(
                    "confirmPassword",
                    "password.mismatch",
                    "Le password non coincidono."
            );
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {

            userService.register(registrationRequest);

            redirectAttributes.addFlashAttribute(
                    "registrationSuccess",
                    "Registrazione completata con successo. Ora puoi effettuare il login."
            );

            return "redirect:/login?registered";

        } catch (UserService.RegistrationException exception) {

            bindingResult.rejectValue(
                    exception.getField(),
                    "registration.error",
                    exception.getMessage()
            );

            return "register";
        }
    }
}
