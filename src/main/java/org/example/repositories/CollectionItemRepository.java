package org.example.repositories;


import org.example.entities.Book;
import org.example.entities.CollectionItem;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Integer> {
    Optional<CollectionItem> findByCreatorAndBook(User u, Book b);

}
