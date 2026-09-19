package it.skillfactory.eco.controller;

import it.skillfactory.eco.service.AvatarService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/profile")
public class UserAvatarController {

    private final AvatarService avatarService;

    public UserAvatarController(
            AvatarService avatarService) {

        this.avatarService = avatarService;
    }

    @PostMapping("/avatar")
    public String uploadAvatar(
            Authentication authentication,
            @RequestParam("avatar") MultipartFile avatar,
            RedirectAttributes redirectAttributes) {

        try {

            avatarService.updateAvatar(
                    authentication.getName(),
                    avatar
            );

            redirectAttributes.addFlashAttribute(
                    "avatarSuccess",
                    "Avatar aggiornato con successo."
            );

        } catch (AvatarService.AvatarException exception) {

            redirectAttributes.addFlashAttribute(
                    "avatarError",
                    exception.getMessage()
            );
        }

        return "redirect:/user/profile";
    }

    @PostMapping("/avatar/delete")
    public String deleteAvatar(
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        avatarService.removeAvatar(
                authentication.getName()
        );

        redirectAttributes.addFlashAttribute(
                "avatarSuccess",
                "Avatar rimosso con successo."
        );

        return "redirect:/user/profile";
    }
}