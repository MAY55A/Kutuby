package org.example.services;

import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.Comment;
import org.example.entities.User;

import java.util.List;

public interface ICollectionService {

    long getTotal();

    public List<Collection> findAll();
    public Collection findByIdCollection(Integer id);
    public Collection findByCreator(User user);
    public List<Collection> findByName(String name);

    Collection findByNameAndCreator(String name, User user);

    //CRUD
    public Collection addCollection(Collection col);
    public void DeleteCollection(Integer id);
    public Collection updateCollection(Integer id , Collection col);


    // collection manipulation

    void addItem(CollectionItem item, Integer coll);

    void removeItem(CollectionItem item, Integer coll);

    public void addComment(Comment c, Integer idCol);

    Collection viewCollection(Collection c);
}
