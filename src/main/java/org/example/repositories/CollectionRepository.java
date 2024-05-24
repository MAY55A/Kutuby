package org.example.repositories;

import org.example.entities.Collection;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Collection findByOwner(User owner);
    Collection findByName(String name);

    @Query("SELECT c FROM Collection c WHERE c.visibility != 'Private'")
    List<Collection> findNotPrivate();
}
