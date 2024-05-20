package org.example.controllers;

import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.entities.User;
import org.example.services.BookService;
import org.example.services.CollectionService;
import org.example.services.UserService;
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
   @Autowired
   private UserService userService;
    @GetMapping("/")
    public String dashboard(Model model) {
        return "Admin/dashboard";
    }

    @PostMapping("/login")
    public String signupUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             Model model) {
        // Login process ...
        // If successful, then redirect to the dashboard
        return "redirect:/admin/dashboard";
        // Else handle the case accordingly
    }

    @GetMapping("/users")
    public String viewUsersPage() {
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
            //cas derreur
            return "redirect:/error/not_found";
        }
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findByIdUser(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "Admin/users/UpdateUser";
        } else {
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Integer id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id")Integer id) {
        userService.DeleteUser(id);
        return "redirect:/admin/users/users";
    }
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "Admin/books/books";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "Admin/books/AddBook";
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
            return "Admin/books/UpdateBook";
        } else {
            return "redirect:/admin/books/books";
        }
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable("id") Integer id, @ModelAttribute("book") Book book) {
        bookService.updateBook(id, book);
        return "redirect:/admin/books/books";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            bookService.DeleteBook(book);
        }
        return "redirect:/admin/books/books";
    }

    @GetMapping("/collections")
    public String getAllCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "Admin/collections/collections";
    }
// please check paths !!!!
    @GetMapping("/collections/add")
    public String showAddCollectionForm(Model model) {
        model.addAttribute("collection", new Collection());
        return "Admin/collections/AddCollection";
    }

    @PostMapping("/collections/add")
    public String addCollection(@ModelAttribute("collection") Collection collection) {
        collectionService.addCollection(collection);
        return "redirect:/admin/collections/collections";
    }

    @GetMapping("/collections/edit/{id}")
    public String showEditCollectionForm(@PathVariable("id") Integer id, Model model) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            model.addAttribute("collection", collection);
            return "Admin/collections/UpdateCollection";
        } else {
            return "redirect:/admin/collections/collections";
        }
    }

    @PostMapping("/collections/edit/{id}")
    public String updateCollection(@PathVariable("id") Integer id, @ModelAttribute("collection") Collection collection) {
        collectionService.updateCollection(id, collection);
        return "redirect:/admin/collections/collections";
    }

    @PostMapping("/collections/delete/{id}")
    public String deleteCollection(@PathVariable("id") Integer id) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            collectionService.DeleteCollection(id);
        }
        return "redirect:/admin/collections/collections";
    }
}
