package org.example.repositories;

import org.example.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    Book findByTitle(String title);

    Set<Book> findByAuthor(String author);

    Set<Book> findByGenres(String genre);

    // chercher un livre par l anne de publication - SQL Query
    @Query("SELECT b FROM Book b WHERE YEAR(b.publishedAt) = ?1")
    Set<Book> findByPublishedYear(int year);
}
