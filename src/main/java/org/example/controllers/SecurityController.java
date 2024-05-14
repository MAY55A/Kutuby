package org.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/pasautorise")
    public String pasautorisé() {
        return "pasautorise"; // on va creer une page pasautorise.html contient un message derrur
    }
    @GetMapping("/login")
    public String login() {
        return "login"; // meme chose pour login.html
    }

}
