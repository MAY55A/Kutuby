package org.example.controllers;

import org.example.entities.Book;
import org.example.entities.Comment;
import org.example.entities.Genre;
import org.example.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

 @GetMapping("/all")
    public String getAllBooks(@RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                              @RequestParam(value = "order", required = false, defaultValue = "asc") String order,
                              @RequestParam(value = "genre", required = false) String genre,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "weight", required = false) Integer weight,
                              @RequestParam(value = "publishedYear", required = false) Integer publishedYear,
                              Model model) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort); // Set page size to maximum

        Specification<Book> specification = Specification.where(null);

        if (genre != null && !genre.isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.isMember(Genre.valueOf(genre.toUpperCase()), root.get("genres")));
        }

        if (author != null && !author.isEmpty()) {
            specification = specification.and((root, query, builder) ->
                    builder.like(root.get("author"), "%" + author + "%"));
        }

        if (weight != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("weight"), weight));
        }

        if (publishedYear != null) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(builder.function("year", Integer.class, root.get("publishedAt")), publishedYear));
        }

        List<Book> books = bookService.findAllFilteredAndSorted(specification, pageable);
        model.addAttribute("books", books);
        return "Guest/books";
    }

    @GetMapping("/{genre}")
    public String getBooksByGenre(@PathVariable("genre") String genre, Model model) {
        Set<Book> books = bookService.findByGenre(genre);
        if (books == null) {
            return "Errors/not_found";
        }
        model.addAttribute("books", books);
        return "Guest/books";
    }

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        if (book == null) {
            return "Errors/not_found";
        }
        model.addAttribute("book", book);
        return "Guest/book";
    }

    @PostMapping("/{bookId}/comments")
    public ResponseEntity<Comment> addCommentToBook(@PathVariable Integer bookId, @RequestBody Comment comment) {
        Book book = bookService.findByIdBook(bookId);
        if (book != null) {
            bookService.addComment(comment, bookId);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/add")
    public String viewAddPage() {
        return "Admin/books/AddBook";
    }
    @PostMapping
    public String addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return "redirect:/admin/books";
    }
    @GetMapping("/update/{id}")
    public String viewUpdatePage(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        if (book == null) {
            return "Errors/not_found";
        }
        model.addAttribute("book", book);
        return "Admin/books/UpdateBook";
    }
    @PutMapping("/{id}")
    public String updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            Book updated = bookService.updateBook(id, updatedBook);
            return "redirect:/admin/books";
        } else {
            return "Errors/not_found";
        }
    }

    @GetMapping("delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            bookService.DeleteBook(book);
            return "redirect:/admin/books";
        } else {
            return "Errors/not_found";
        }
    }
}
