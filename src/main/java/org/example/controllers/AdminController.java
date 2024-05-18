package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AdminController {

    @GetMapping("/admin/books")
    public String getAdminBooksPage() {
        return "Admin_Books"; // Returns Admin_books.html
    }

    @GetMapping("/admin/collections")
    public String getAdminCollectionsPage() {
        return "Admin_Collections"; // Returns Admin_Collections.html
    }

    @GetMapping("/admin/users")
    public String getAdminUsersPage() {
        return "Admin_Users"; // Returns Admin_Users.html
    }
}
