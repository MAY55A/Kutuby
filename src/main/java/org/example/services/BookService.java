package org.example.services;

import org.example.entities.*;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
@Override
    public List<Book> findAllSorted(String sortBy, String order) {
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy != null ? sortBy : "id");

        return bookRepository.findAll(sort);
    }
    @Override
    public Book findByIdBook(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Set<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Set<Book> findByGenre(String genre) {
        return bookRepository.findByGenres(genre);
    }

    @Override
    public Set<Book> findByYear(int year) {
        return bookRepository.findByPublishedYear(year);
    }

    @Override
    public Book addBook(Book b) {
        return bookRepository.save(b);
    }

    @Override
    public void DeleteBook(Book b) {

        bookRepository.delete(b);
    }

    @Override
    public Book updateBook(Integer id, Book book) {
        // chercher book par son id
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            // Mise ajour des champs a partir de var book
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setLanguage(book.getLanguage());
            existingBook.setCoverImage(book.getCoverImage());
            existingBook.setGenres(book.getGenres());
            existingBook.setPublishedAt(book.getPublishedAt());
            existingBook.setDescription(book.getDescription());
            existingBook.setWeight(book.getWeight());
            existingBook.setComments(book.getComments());
            existingBook.setCollectionItems(book.getCollectionItems());

           // sauvgarder les donner
            return bookRepository.save(existingBook);
        } else {
            // sinon retourner nulle
            return null;
        }
    }
    public float getRating(Integer id) {
        Book book = bookRepository.findById(id).get();
        Stream<Short> ratings = book.getCollectionItems().stream().filter(item -> nonNull(item.getRating())).map(item -> item.getRating());
        return (float) ratings.reduce((short) 0, (rating1, rating2) -> (short) (rating1 + rating2)) / ratings.count();
    }
    public void addComment(Comment c, Integer bo) {
        Book book = bookRepository.findById(bo).get();
        book.getComments().add(c);
        bookRepository.save(book);
    }

}
