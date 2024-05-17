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
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
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

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            Book updated = bookService.updateBook(id, updatedBook);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        Book book = bookService.findByIdBook(id);
        if (book != null) {
            bookService.DeleteBook(book);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}