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
    private final CollectionItemRepository collectionItemRepository;
    private final RankingRepository rankingRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository, CollectionItemRepository collectionItemRepository, RankingRepository rankingRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionItemRepository = collectionItemRepository;
        this.rankingRepository = rankingRepository;
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
        Collection savedCollection = collectionRepository.save(col);
        updateUserScore(col.getOwner(), 10); // incremantation point lorsque user ajout collection
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
    public void addItem(CollectionItem item, Integer coll) {
        Collection collection = collectionRepository.findById(coll).get();
        collection.getItems().add(item);
        collectionRepository.save(collection);
        updateUserScore(collection.getOwner(), 5); //incremantation point lorsque ajout item
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
        updateUserScore(c.getUser(), 3); // incremantation point lorsque user ajout commentaire
    }

    //MAJ des scores de users par week /month/year
    private void updateUserScore(User user, int points) {
        updateRanking(user, points, RankingPeriod.Week);
        updateRanking(user, points, RankingPeriod.Month);
        updateRanking(user, points, RankingPeriod.Year);
    }

    private void updateRanking(User user, int points, RankingPeriod period) {
        Ranking ranking = rankingRepository.findByUserAndPeriod(user, period);
        if (ranking != null) {
            ranking.incrementPoints(points);
            rankingRepository.save(ranking);
        } else {
            Ranking newRanking = new Ranking(period);
            newRanking.setUser(user);
            newRanking.incrementPoints(points);
            rankingRepository.save(newRanking);
        }
    }
    @Override
    public Collection viewCollection(Collection c) {
        c.setViews(c.getViews()+1);
        return collectionRepository.save(c);
    }
}
