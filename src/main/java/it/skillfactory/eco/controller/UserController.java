package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.AuthStyleSettings;
import it.skillfactory.eco.model.User;
import it.skillfactory.eco.repository.AuthStyleSettingsRepository;
import it.skillfactory.eco.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private final UserService userService;

    private final AuthStyleSettingsRepository authStyleSettingsRepository;


    public UserController(
            UserService userService,
            AuthStyleSettingsRepository authStyleSettingsRepository) {

        this.userService = userService;

        this.authStyleSettingsRepository =
                authStyleSettingsRepository;
    }


    /*
     * =========================================================
     * LOGIN UTENTE
     * =========================================================
     */

    @GetMapping("/login-user")
    public String loginPage(Model model) {

        AuthStyleSettings settings =
                getAuthStyleSettings();

        model.addAttribute("authStyle", settings);

        return "login-user";
    }


    /*
     * =========================================================
     * REGISTRAZIONE
     * =========================================================
     */

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute(
                "user",
                new User()
        );

        return "register";
    }


    @PostMapping("/register")
    public String registerSubmit(
            @ModelAttribute("user") User user,
            Model model) {

        try {

            userService.registerUser(user);

            return "redirect:/login-user?registered=true";

        } catch (Exception e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            return "register";
        }
    }


    /*
     * =========================================================
     * DASHBOARD / PROFILO UTENTE
     * =========================================================
     */

    @GetMapping("/user/dashboard")
    public String userDashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user =
                userService.getByEmail(
                        userDetails.getUsername()
                );

        model.addAttribute(
                "user",
                user
        );


        AuthStyleSettings settings =
                getAuthStyleSettings();

        model.addAttribute(
                "authStyle",
                settings
        );


        return "user/profile";
    }


    /*
     * =========================================================
     * AGGIORNAMENTO PROFILO
     * =========================================================
     */

    @PostMapping("/user/profile/update")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("nome") String nome,
            @RequestParam("cognome") String cognome,
            @RequestParam(
                    value = "foto",
                    required = false
            ) MultipartFile foto) {

        try {

            userService.updateProfile(
                    userDetails.getUsername(),
                    nome,
                    cognome,
                    foto
            );

            return "redirect:/user/dashboard?success=true";

        } catch (Exception e) {

            return "redirect:/user/dashboard?error=true";
        }
    }


    /*
     * =========================================================
     * RECUPERO IMPOSTAZIONI COLORI
     * =========================================================
     *
     * Se per qualsiasi motivo la configurazione non esiste
     * ancora, viene creata automaticamente con i valori
     * predefiniti definiti nell'entità AuthStyleSettings.
     */

    private AuthStyleSettings getAuthStyleSettings() {

        return authStyleSettingsRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() ->
                        authStyleSettingsRepository.save(
                                new AuthStyleSettings()
                        )
                );
    }
}