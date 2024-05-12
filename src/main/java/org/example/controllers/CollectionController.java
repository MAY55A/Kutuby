package org.example.controllers;

import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.Comment;

import org.example.services.IBookService;
import org.example.services.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {


    private final IBookService bookService; // Inject the BookService
    private final ICollectionService collectionService;

    @Autowired
    public CollectionController(ICollectionService collectionService, IBookService bookService) {
        this.collectionService = collectionService;
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.findAll();
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Integer id) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            return new ResponseEntity<>(collection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Collection> addCollection(@RequestBody Collection collection) {
        Collection addedCollection = collectionService.addCollection(collection);
        return new ResponseEntity<>(addedCollection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(@PathVariable Integer id, @RequestBody Collection collection) {
        Collection updatedCollection = collectionService.updateCollection(id, collection);
        if (updatedCollection != null) {
            return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Integer id) {
        collectionService.DeleteCollection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{collectionId}/items/{itemId}")
    public ResponseEntity<Void> addItemToCollection(@PathVariable Integer collectionId, @PathVariable Integer itemId) {
        // Retrieve the collection and item from their respective services
        Collection collection = collectionService.findByIdCollection(collectionId);
        Book book = bookService.findByIdBook(itemId);

        // Check if both the collection and book exist
        if (collection == null || book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Create a new CollectionItem with the book and collection
        CollectionItem collectionItem = new CollectionItem();
        collectionItem.setBook(book);
        collectionItem.setCollection(collection);
        collectionItem.setAddedAt(new Date()); // Set the current date

        // Add the collection item to the collection
        collectionService.addItemToCollection(collectionItem);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{collectionId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCollection(@PathVariable Integer collectionId, @PathVariable Integer itemId) {
        // Retrieve the collection item by collectionId and itemId
        CollectionItem collectionItem = collectionService.findCollectionItemById(collectionId, itemId);

        // Check if the collection item exists
        if (collectionItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Remove the collection item from the collection
        collectionService.removeItemFromCollection(collectionItem);

        return new ResponseEntity<>(HttpStatus.OK);
    }


   /* @PostMapping("/{collectionId}/comments")
    public ResponseEntity<Void> addCommentToCollection(@PathVariable Integer collectionId, @RequestBody Comment comment) {
        // Retrieve the collection
        Collection collection = collectionService.findByIdCollection(collectionId);

        // Check if the collection exists
        if (collection == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Add the comment to the collection
        collectionService.addCommentToCollection(comment, collectionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    // Add other endpoints for collection manipulation (e.g., adding/removing items, adding comments) if needed
}
