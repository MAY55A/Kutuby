package org.example.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index()
    {
        return "Guest/home";
    }


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String viewBooksPage()
    {
        return "redirect:/books/all";
    }
    @RequestMapping(value = "/collections", method = RequestMethod.GET)
    public String viewCollectionPage()
    {
        return "redirect:/collections/all";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String viewAdminPage()
    {
        return "redirect:/admin/dashboard";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String viewSignupPage()
    {
        return "Guest/sign_up";
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String viewProfilePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                    return "redirect:/admin";
                }
            }
            return "redirect:/profile/home";
        }
        throw new IllegalStateException("User not authenticated");
    }
    @RequestMapping(value = "/about_us", method = RequestMethod.GET)
    public String viewAboutUsPage()
    {
        return "Guest/about_us";
    }
    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public String viewLeaderBoardPage()
    {
        return "Guest/leaderboard";
    }
}
