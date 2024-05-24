package org.example.services;

import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.User;

import java.util.List;

public interface ICollectionItemService {
    public List<CollectionItem> findAll();
    public CollectionItem findByIdCollectionItem(Integer id);

    CollectionItem findByUserAndBook(User u, Book b);

    //CRUD
    public CollectionItem addCollectionItem(CollectionItem col); // add a book to a collection
    public void DeleteCollectionItem(Integer id);
    public CollectionItem updateCollectionItem(Integer id , CollectionItem col);





}
