package org.example.services;

import org.example.entities.Book;
import org.example.entities.Comment;
import org.example.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;

public interface IBookService {

    public List<Book> findAll();
    //filtrage
    public Book findByIdBook(Integer id);
    public Book findByTitle(String title);
    public Set<Book> findByAuthor(String author);
    public Set<Book> findByGenre(String genre);

    public Set<Book> findByYear(int year);
    List<Book> findAllFilteredAndSorted(Specification<Book> spec, Pageable pageable);




    //CRUD
    public Book addBook(Book b);
    public void DeleteBook(Book id);
    public Book updateBook(Integer id , Book book);
    void addComment(Comment comment, Integer bookId);

}
