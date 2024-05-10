package org.example.services;

import org.example.entities.CollectionItem;
import org.example.repositories.CollectionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionItemService implements ICollectionItemService {

    private final CollectionItemRepository collectionItemRepository;

    @Autowired
    public CollectionItemService(CollectionItemRepository collectionItemRepository) {
        this.collectionItemRepository = collectionItemRepository;
    }

    @Override
    public List<CollectionItem> findAll() {
        return collectionItemRepository.findAll();
    }

    @Override
    public CollectionItem findByIdCollectionItem(Integer id) {
        Optional<CollectionItem> optionalCollectionItem = collectionItemRepository.findById(id);
        return optionalCollectionItem.orElse(null);
    }

    @Override
    public CollectionItem addCollectionItem(CollectionItem col) {
        return collectionItemRepository.save(col);
    }

    @Override
    public void DeleteCollectionItem(Integer id) {
        collectionItemRepository.deleteById(id);
    }

    @Override
    public CollectionItem updateCollectionItem(Integer id, CollectionItem col) {
        Optional<CollectionItem> optionalCollectionItem = collectionItemRepository.findById(id);
        if (optionalCollectionItem.isPresent()) {
            CollectionItem existingCollectionItem = optionalCollectionItem.get();
            existingCollectionItem.setAddedAt(col.getAddedAt());
            existingCollectionItem.setStartedReadingAt(col.getStartedReadingAt());
            existingCollectionItem.setFinishedReadingAt(col.getFinishedReadingAt());
            existingCollectionItem.setReadingProgress(col.getReadingProgress());
            existingCollectionItem.setRating(col.getRating());
            existingCollectionItem.setBook(col.getBook());
            existingCollectionItem.setCreator(col.getCreator());
            return collectionItemRepository.save(existingCollectionItem);
        } else {
            return null; // ou bien nous faison une exception avec un message derreur
        }
    }
}

