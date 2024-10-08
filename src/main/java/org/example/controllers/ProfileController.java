package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.CollectionType;
import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @GetMapping("/home")

    public String viewProfile(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userService.getCurrentUser());
        model.addAttribute("reading", user.getCollections().stream().filter(collection -> collection.getType() == CollectionType.Reading).findFirst().get().getItems().stream().limit(5).collect(Collectors.toList()));
        model.addAttribute("completed", user.getCollections().stream().filter(collection -> collection.getType() == CollectionType.Completed).findFirst().get().getItems().stream().limit(5).collect(Collectors.toList()));
        return "User/user_account";
    }

    @GetMapping("/collections")
    public String viewCollections(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "User/myCollections";
    }

    @GetMapping("/favorites")
    public String viewFavorites(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "User/myFavorites";
    }

    @GetMapping("/settings")
    public String viewSettings(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "User/settings";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }
}
