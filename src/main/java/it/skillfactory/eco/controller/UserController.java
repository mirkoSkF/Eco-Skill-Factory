package it.skillfactory.eco.controller;

import it.skillfactory.eco.dto.ProfileUpdateRequest;
import it.skillfactory.eco.model.User;
import it.skillfactory.eco.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model) {

        User user = userService.findByUsername(
                authentication.getName()
        );

        ProfileUpdateRequest profile =
                new ProfileUpdateRequest(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                );

        model.addAttribute("user", user);
        model.addAttribute("profileRequest", profile);

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            Authentication authentication,
            @Valid @ModelAttribute("profileRequest")
            ProfileUpdateRequest profileRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            User user = userService.findByUsername(
                    authentication.getName()
            );

            model.addAttribute("user", user);

            return "profile";
        }

        try {

            userService.updateProfile(
                    authentication.getName(),
                    profileRequest
            );

            redirectAttributes.addFlashAttribute(
                    "profileSuccess",
                    "Profilo aggiornato con successo."
            );

            return "redirect:/user/profile";

        } catch (UserService.ProfileUpdateException exception) {

            bindingResult.rejectValue(
                    exception.getField(),
                    "profile.error",
                    exception.getMessage()
            );

            User user = userService.findByUsername(
                    authentication.getName()
            );

            model.addAttribute("user", user);

            return "profile";
        }
    }
}
