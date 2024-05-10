package org.example.services;

import org.example.entities.Collection;
import org.example.entities.CollectionItem;

import java.util.List;

public interface ICollectionItemService {
    public List<CollectionItem> findAll();
    public CollectionItem findByIdCollectionItem(Integer id);

    //CRUD
    public CollectionItem addCollectionItem(CollectionItem col); // add a book to a collection
    public void DeleteCollectionItem(Integer id);
    public CollectionItem updateCollectionItem(Integer id , CollectionItem col);





}
