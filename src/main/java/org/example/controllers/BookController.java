package org.example.controllers;

import org.example.entities.Book;
import org.example.entities.Comment;
import org.example.entities.Genre;
import org.example.services.IBookService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(IBookService bookService, ProfileController profileController, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getAllBooks(@RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                              @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                              @RequestParam(value = "genre", required = false) String genre,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "publishedYear", required = false) Integer publishedYear,
                              @RequestParam(value = "bookName", required = false) String bookName, // Add bookName parameter
                              Model model) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort); // Set page size to maximum

        Specification<Book> specification = Specification.where(null);

        if (genre != null && !genre.isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.isMember(Genre.valueOf(genre.toUpperCase()), root.get("genres")));
        }
        if (bookName != null && !bookName.isEmpty()) { // Include filtering by bookName
            specification = specification.and((root, query, builder) ->
                    builder.like(root.get("title"), "%" + bookName + "%"));
        }
        if (author != null && !author.isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.like(root.get("author"), "%" + author + "%"));
        }

        if (publishedYear != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(builder.function("year", Integer.class, root.get("publishedAt")), publishedYear));
        }

        List<Book> books = bookService.findAllFilteredAndSorted(specification, pageable);
        model.addAttribute("books", books);
        model.addAttribute("topBooks", bookService.findTop());
        model.addAttribute("genreList", Genre.values()); // Pass the genre list to the model
        return "Guest/books";
    }
    @GetMapping("/{genre}")
    public String getBooksByGenre(@PathVariable("genre") String genre, Model model) {
        Set<Book> books = bookService.findByGenre(genre);
        if (books == null) {
            return "Errors/not_found";
        }
        model.addAttribute("books", books);
        model.addAttribute("topBooks", bookService.findTop());
        return "Guest/books";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        if (book == null) {
            return "Errors/not_found";
        }
        model.addAttribute("book", book);
        model.addAttribute("user", userService.getCurrentUser());
        return "Guest/book";
    }

    @PostMapping("/{bookId}/comments")
    @PreAuthorize("hasRole('USER')")
    public String addCommentToBook(@PathVariable Integer bookId, @ModelAttribute Comment comment) {
        Book book = bookService.findByIdBook(bookId);
        if (book != null) {
            comment.setUser(userService.getCurrentUser());
            bookService.addComment(comment, bookId);
            return "redirect:/books/book/"+bookId;
        } else {
            return "Errors/not_found";
        }
    }
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAddPage(Model model) {
        model.addAttribute("genres", Genre.values());
        return "Admin/books/AddBook";
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Book successfully added !");
        return "redirect:/admin/books";
    }
    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewUpdatePage(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        if (book == null) {
            return "Errors/not_found";
        }
        model.addAttribute("book", book);
        model.addAttribute("genres", Genre.values());
        return "Admin/books/UpdateBook";
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBook(@PathVariable Integer id, @ModelAttribute Book updatedBook, RedirectAttributes redirectAttributes) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            bookService.updateBook(id, updatedBook);
            redirectAttributes.addFlashAttribute("message", "Book successfully updated!");
            return "redirect:/admin/books";
        } else {
            return "Errors/not_found";
        }
    }

    @GetMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            bookService.DeleteBook(book);
            redirectAttributes.addFlashAttribute("message", "Book successfully deleted !");
            return "redirect:/admin/books";
        } else {
            return "Errors/not_found";
        }
    }
}
