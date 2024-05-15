package org.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/pasautorise")
    public String pasautorisé() {
        return "pasautorise"; //  pasautorise.html contient un message derrur
    }
    @GetMapping("/login")
    public String login() {
        return "sign_in"; // redirection a sign in page
    }

}
