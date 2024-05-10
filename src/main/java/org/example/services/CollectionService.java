package org.example.services;

import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.Comment;
import org.example.entities.User;
import org.example.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService implements ICollectionService {

    private final CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    @Override
    public Collection findByIdCollection(Integer id) {
        Optional<Collection> optionalCollection = collectionRepository.findById(id);
        return optionalCollection.orElse(null);
    }

    @Override
    public Collection findByCreator(User user) {
        return collectionRepository.findByOwner(user);
    }

    @Override
    public Collection findByName(String name) {
        return collectionRepository.findByName(name);
    }

    @Override
    public Collection addCollection(Collection col) {
        return collectionRepository.save(col);
    }

    @Override
    public void DeleteCollection(Integer id) {
        collectionRepository.deleteById(id);
    }

    @Override
    public Collection updateCollection(Integer id, Collection col) {
        Optional<Collection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            Collection existingCollection = optionalCollection.get();
            existingCollection.setName(col.getName());
            existingCollection.setType(col.getType());
            existingCollection.setVisibility(col.getVisibility());
            existingCollection.setDescription(col.getDescription());
            existingCollection.setCoverImage(col.getCoverImage());
            return collectionRepository.save(existingCollection);
        } else {
            return null;
        }
    }

    @Override
    public void addItemToCollection(CollectionItem ci) {
        Collection collection = ci.getCollection();
        collection.getItems().add(ci);
        collectionRepository.save(collection);
    }

    @Override
    public void removeItemFromCollection(CollectionItem ci) {
        Collection collection = ci.getCollection();
        collection.getItems().remove(ci);
        collectionRepository.save(collection);
    }

    @Override
    public void addComment(Comment c, Integer idCol) {
        Collection collection = collectionRepository.findById(idCol).get();
        collection.getComments().add(c);
        collectionRepository.save(collection);



    }
}
