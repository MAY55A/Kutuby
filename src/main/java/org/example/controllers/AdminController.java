package org.example.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.services.BookService;
import org.example.services.CollectionService;
import org.example.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.security.access.annotation.Secured;
=======
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
>>>>>>> ce79405141e623828ad18441bfffede37f12562c
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
    private ProfileController profileController;
    @Autowired
    private IUserService userService;

<<<<<<< HEAD
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        return "/Admin/dashboard";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        // Login process ...
        // If successful, then redirect to the dashboard
        return "redirect:/admin/dashboard";
        // Else handle the case accordingly
    }

    @GetMapping("/users")
    public String viewUsersPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "Admin/users/users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "Admin/users/addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email,
                          @RequestParam("confirmPassword") String confirmPassword) {
        try {
            userService.addUser(username, password, email, confirmPassword);
            return "redirect:/admin/users";
        } catch (RuntimeException e) {
            // Handle error case
            return "redirect:/error/not_found";
        }
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findByIdUser(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "Admin/users/updateUser";
        } else {
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.DeleteUser(id);
        return "redirect:/admin/users";
    }

=======
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin/dashboard";
    }
>>>>>>> ce79405141e623828ad18441bfffede37f12562c
    @GetMapping("/login")
    public String viewLoginPage() {
        return "Admin/login";
    }

<<<<<<< HEAD
=======
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String viewUsersPage() {
        return "redirect:/users/all";
    }
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.DeleteUser(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/delete-collection/{id}")
    public String deleteCollection(@PathVariable Integer id) {
        collectionService.DeleteCollection(id);
        return "redirect:/admin/collections";
    }

>>>>>>> ce79405141e623828ad18441bfffede37f12562c
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "Admin/books/books";
    }

<<<<<<< HEAD
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "Admin/books/addBook";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "Admin/books/updateBook";
        } else {
            return "redirect:/admin/books";
        }
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable("id") Integer id, @ModelAttribute("book") Book book) {
        bookService.updateBook(id, book);
        return "redirect:/admin/books";
    }

    @PostMapping("/books/delete/{id}") public String deleteBook(@PathVariable("id") Integer id) {
        Book book = bookService.findByIdBook(id);
        if (book != null) { bookService.DeleteBook(book); }
        return "redirect:/admin/books/books"; }

=======
>>>>>>> ce79405141e623828ad18441bfffede37f12562c
    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "Admin/collections";
    }
<<<<<<< HEAD

    @GetMapping("/collections/add")
    public String showAddCollectionForm(Model model) {
        model.addAttribute("collection", new Collection());
        return "Admin/collections/addCollection";
    }

    @PostMapping("/collections/add")
    public String addCollection(@ModelAttribute("collection") Collection collection) {
        collectionService.addCollection(collection);
        return "redirect:/admin/collections";
    }

    @GetMapping("/collections/edit/{id}")
    public String showEditCollectionForm(@PathVariable("id") Integer id, Model model) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            model.addAttribute("collection", collection);
            return "Admin/collections/updateCollection";
        } else {
            return "redirect:/admin/collections";
=======
    @GetMapping("/settings")
    public String getSettings(Model model) {
        model.addAttribute("user", profileController.getCurrentUser());
        return "Admin/settings";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
>>>>>>> ce79405141e623828ad18441bfffede37f12562c
        }
        return "redirect:/admin/login";
    }
<<<<<<< HEAD

    @PostMapping("/collections/delete/{id}")
    public String deleteCollection(@PathVariable("id") Integer id) {
        collectionService.DeleteCollection(id);
        return "redirect:/admin/collections";
    }
=======
>>>>>>> ce79405141e623828ad18441bfffede37f12562c
}



