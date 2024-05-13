package org.example.services;

import org.example.entities.Book;
import org.example.entities.Comment;
import org.example.entities.User;

import java.util.List;
import java.util.Set;

public interface IBookService {

    public List<Book> findAll();
    //filtrage
    public Book findByIdBook(Integer id);
    public Book findByTitle(String title);
    public Set<Book> findByAuthor(String author);
    public Set<Book> findByGenre(String genre);

    public Set<Book> findByYear(int year);




    //CRUD
    public Book addBook(Book b);
    public void DeleteBook(Book b);
    public Book updateBook(Integer id , Book book);
    void addComment(Comment comment, Integer bookId);

}
