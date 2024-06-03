package org.example.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.entities.RankingPeriod;
import org.example.services.BookService;
import org.example.services.CollectionService;
import org.example.services.IUserService;
import org.example.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;



@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RankingService rankingService;
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", bookService.getTotal());
        model.addAttribute("totalCollections", collectionService.getTotal());
        model.addAttribute("totalUsers", userService.getTotal()-1);
        model.addAttribute("topWeeklyReaders", rankingService.getTop3RankingsByPeriod(RankingPeriod.Week));
        model.addAttribute("topMonthlyReaders", rankingService.getTop3RankingsByPeriod(RankingPeriod.Month));
        model.addAttribute("topYearlyReaders", rankingService.getTop3RankingsByPeriod(RankingPeriod.Year));
        return "Admin/dashboard";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "Admin/login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsersPage(@RequestParam(value = "name", required = false) String name) {
        if(name != null && !name.isEmpty())
            return "redirect:/users/all?name=" + name;
        return "redirect:/users/all";
    }
    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        userService.DeleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User successfully deleted !");
        return "redirect:/users/all";
    }

    @GetMapping("/books")
    public String getAllBooks(@RequestParam(value = "message", required = false) String message, @RequestParam(value = "name", required = false) String name, Model model) {
        List<Book> books = bookService.findAll();
        if(name != null && !name.isEmpty()) {
            model.addAttribute("books", bookService.findByTitle(name));
            model.addAttribute("name", name);
        } else
            model.addAttribute("books", books);
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "Admin/books/books";
    }

    @GetMapping("/collections")
    public String getAllCollections(@RequestParam(value = "message", required = false) String message, @RequestParam(value = "name", required = false) String name, Model model) {
        List<Collection> collections = collectionService.findAll();
        if(name != null && !name.isEmpty()) {
            model.addAttribute("collections", collectionService.findByName(name));
            model.addAttribute("name", name);
        } else
            model.addAttribute("collections", collections);
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "Admin/collections";
    }
    @GetMapping("/collections/delete/{id}")
    public String deleteCollection(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        collectionService.DeleteCollection(id);
        redirectAttributes.addFlashAttribute("message", "Collection successfully deleted !");
        return "redirect:/admin/collections";
    }
    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "Admin/settings";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/admin/login";
    }
}



