package org.example.controllers;

import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signupUser(@RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("confirmPassword") String confirmPassword,
                             Model model) {
        try {
            User addedUser = userService.addUser(name, email, password, confirmPassword);
            model.addAttribute("user", addedUser);
            return "User/user_account";
        } catch (Exception e) {
            if ("exists".equals(e.getMessage())) {
                model.addAttribute("msg", "User already exists!");
            } else if ("password_mismatch".equals(e.getMessage())) {
                model.addAttribute("msg", "The confirmation password does not match!");
            } else {
                model.addAttribute("msg", "An error occurred: " + e.getMessage());
            }
            return "Guest/sign_up";
        }
    }
}
