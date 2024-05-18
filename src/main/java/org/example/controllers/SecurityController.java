package org.example.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/unauthorised")
    public String unauthorised() {
        return "Errors/unauthorised";
    }
    @GetMapping("/login")
    public String login() {
        return "Guest/sign_in";
    }

}
