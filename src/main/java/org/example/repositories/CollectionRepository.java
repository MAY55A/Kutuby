package org.example.repositories;

import org.example.entities.Collection;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Collection findByOwner(User owner);
    Collection findByName(String name);
}
