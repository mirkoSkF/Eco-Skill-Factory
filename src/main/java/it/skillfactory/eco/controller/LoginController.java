package it.skillfactory.eco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Mappa sia /login, /login.html e /admin-login per servire correttamente templates/login.html
    @GetMapping({"/login", "/login.html", "/admin-login"})
    public String login() {
        return "login";
    }
}