package org.example.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.services.BookService;
import org.example.services.CollectionService;
import org.example.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin/dashboard";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "Admin/login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsersPage() {
        return "redirect:/users/all";
    }
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.DeleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "Admin/books/books";
    }

    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "Admin/collections";
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



