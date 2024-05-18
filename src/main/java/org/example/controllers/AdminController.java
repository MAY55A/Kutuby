package org.example.controllers;


import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.services.BookService;
import org.example.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/")
    public String dashboard(Model model) {
        return "Admin/dashboard";
    }
    @PostMapping("/login")
    public String signupUser(@RequestParam("username") String username,
                             @RequestParam("password") String password, Model model) {
        //login process ...
        //if successful then
        return "redirect:admin/dashboard";
        //else...
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsersPage()
    {
        return "redirect:/users";
    }
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "Admin/books";
    }
    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "Admin/collections";
    }



