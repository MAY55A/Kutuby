package org.example.controllers;

import org.example.entities.CollectionItem;
import org.example.services.ICollectionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-items")
public class CollectionItemController {

    private final ICollectionItemService collectionItemService;

    @Autowired
    public CollectionItemController(ICollectionItemService collectionItemService) {
        this.collectionItemService = collectionItemService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionItem>> getAllCollectionItems() {
        List<CollectionItem> collectionItems = collectionItemService.findAll();
        return new ResponseEntity<>(collectionItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionItem> getCollectionItemById(@PathVariable Integer id) {
        CollectionItem collectionItem = collectionItemService.findByIdCollectionItem(id);
        if (collectionItem != null) {
            return new ResponseEntity<>(collectionItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CollectionItem> addCollectionItem(@RequestBody CollectionItem collectionItem) {
        CollectionItem addedItem = collectionItemService.addCollectionItem(collectionItem);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionItem> updateCollectionItem(@PathVariable Integer id, @RequestBody CollectionItem collectionItem) {
        CollectionItem updatedItem = collectionItemService.updateCollectionItem(id, collectionItem);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionItem(@PathVariable Integer id) {
        CollectionItem collectionItem = collectionItemService.findByIdCollectionItem(id);
        if (collectionItem != null) {
            collectionItemService.DeleteCollectionItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
