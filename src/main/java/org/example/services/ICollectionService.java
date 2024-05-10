package org.example.services;

import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.Comment;
import org.example.entities.User;

import java.util.List;

public interface ICollectionService {

    public List<Collection> findAll();
    public Collection findByIdCollection(Integer id);
    public Collection findByCreator(User user);
    public Collection findByName(String name);

    //CRUD
    public Collection addCollection(Collection col);
    public void DeleteCollection(Integer id);
    public Collection updateCollection(Integer id , Collection col);


    // collection manipulation
    public void addItemToCollection(CollectionItem ci);

    public void removeItemFromCollection(CollectionItem ci);

    public void addComment(Comment c, Integer idCol);






}
