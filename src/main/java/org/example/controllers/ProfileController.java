package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    @GetMapping("/home")

    public String viewProfile(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : getCurrentUser();
        System.out.println(user);
        model.addAttribute("user", user);
        return "User/user_account";
    }

    @GetMapping("/collections")
    public String viewCollections(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : getCurrentUser();
        model.addAttribute("collections", user.getCollections());
        return "User/myCollections";
    }

    @GetMapping("/favorites")
    public String viewFavorites(Model model, @RequestParam(required = false) Integer userId) {
        User user = (userId != null) ? userService.findByIdUser(userId) : getCurrentUser();
        model.addAttribute("favorites", user.getFavourites());
        return "User/myFavorites";
    }

    @GetMapping("/settings")
    public String viewSettings(Model model) {
        model.addAttribute("user", getCurrentUser());
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
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return userService.findByUserName(authentication.getName());
        } else
            throw new IllegalStateException("User not authenticated");
    }
}
