package org.example.services;

import org.example.entities.*;
import org.example.repositories.CollectionItemRepository;
import org.example.repositories.CollectionRepository;
import org.example.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionService implements ICollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionItemService collectionItemService;
    private final RankingService rankingService;
    private final UserService userService;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, CollectionItemRepository collectionItemRepository, CollectionItemService collectionItemService, RankingRepository rankingRepository, RankingService rankingRepository1, RankingService rankingService, UserService userService) {
        this.collectionRepository = collectionRepository;
        this.collectionItemService = collectionItemService;
        this.rankingService = rankingService;
        this.userService = userService;
    }

    @Override
    public long getTotal() {
        return collectionRepository.count();
    }@Override
    public List<Collection> findAll() {
        return collectionRepository.findNotPrivate();
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
    public List<Collection> findByName(String name) {
        return collectionRepository.findByNameContainingIgnoreCase(name);
    }
    @Override
    public Collection findByNameAndCreator(String name, User user) {
        return collectionRepository.findByNameAndOwner(name, user);
    }

    @Override
    public Collection addCollection(Collection col) {
        col.setOwner(userService.getCurrentUser());
        if(findByNameAndCreator(col.getName(), col.getOwner()) != null)
            return null;
        Collection savedCollection = collectionRepository.save(col);
        col.getOwner().getCollections().add(col);
        if(savedCollection.getVisibility() != Visibility.Private)
            rankingService.updateUserScore(col.getOwner(), 70); // incremantation point lorsque user ajout collection visible par les autres
        return savedCollection;
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
            if(existingCollection.getVisibility() == Visibility.Private && col.getVisibility() != Visibility.Private)
                rankingService.updateUserScore(col.getOwner(), 50);
            existingCollection.setName(col.getName());
            existingCollection.setVisibility(col.getVisibility());
            existingCollection.setDescription(col.getDescription());
            existingCollection.setCoverImage(col.getCoverImage());
            return collectionRepository.save(existingCollection);
        } else {
            return null;
        }
    }

    @Override
    public void addItem(CollectionItem item, Integer coll) {
        CollectionItem existingItem = collectionItemService.findByUserAndBook(item.getCreator(), item.getBook());
        Collection collection = collectionRepository.findById(coll).get();
        if (existingItem != null) {
            collectionItemService.updateCollectionItem(existingItem.getId(), item);
            if (collection.getType() != CollectionType.Personnalised) {
                collection.getOwner().getCollections().stream().filter(c -> c.getType() != CollectionType.Personnalised).forEach(col -> removeItem(existingItem, col.getId()));
            }
            item = existingItem;
        } else {
            collectionItemService.addCollectionItem(item);
        }
        collection.getItems().add(item);
        if(collection.getType() == CollectionType.Reading)
            rankingService.updateUserScore(collection.getOwner(), item.getBook().getWeight());
        else if (collection.getType() == CollectionType.Completed)
            rankingService.updateUserScore(collection.getOwner(), item.getBook().getWeight()*2);
        collectionRepository.save(collection);
    }

    @Override
    public void removeItem(CollectionItem item, Integer coll) {
        Collection collection = collectionRepository.findById(coll).get();
        collection.getItems().remove(item);
        collectionRepository.save(collection);
    }

    @Override
    public void addComment(Comment c, Integer idCol) {
        Collection collection = collectionRepository.findById(idCol).get();
        collection.getComments().add(c);
        collectionRepository.save(collection);
        rankingService.updateUserScore(userService.getCurrentUser(), 10); // incremantation point lorsque user ajout commentaire
    }

    @Override
    public Collection viewCollection(Collection c) {
        User user = userService.getCurrentUser();
        if (user == null || c.getOwner() != user) {
            c.setViews(c.getViews() + 1);
            return collectionRepository.save(c);
        }
        return c;
    }
}
