package org.example.controllers;

import org.example.entities.Book;
import org.example.entities.Collection;
import org.example.entities.CollectionItem;
import org.example.entities.Comment;

import org.example.services.IBookService;
import org.example.services.ICollectionItemService;
import org.example.services.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collections")
public class CollectionController {


    private final ICollectionService collectionService;
    private final ICollectionItemService collectionItemService;

    @Autowired
    public CollectionController(ICollectionService collectionService, IBookService bookService, ICollectionItemService collectionItemService) {
        this.collectionService = collectionService;
        this.collectionItemService = collectionItemService;
    }



    @GetMapping("/all")
    public String getAllCollections(Model model, @RequestParam(required = false) String sortBy) {
        List<Collection> collections = collectionService.findAll();

        // Sort collections based on sortBy parameter
        if (sortBy != null) {
            switch (sortBy) {
                case "views":
                    collections.sort(Comparator.comparing(Collection::getViews).reversed());
                    break;
                case "creationDate":
                    collections.sort(Comparator.comparing(Collection::getCreatedAt).reversed());
                    break;
                default:
                    // Handle invalid sortBy parameter
                    model.addAttribute("error", "Invalid sortBy parameter: " + sortBy);
                    // You can choose to return an error page or handle it in another way
                    return "error";
            }
        }

        model.addAttribute("collections", collections);
        return "Guest/collections";
    }


    @GetMapping("/{id}")
    public String getCollectionById(@PathVariable Integer id, Model model) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            collectionService.viewCollection(collection);
            model.addAttribute("collection", collection);
            return "Guest/collection";
        } else {
            return "Errors/not_found";
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
    @PostMapping("/{collectionId}/items")
    public ResponseEntity<Void> addItemToCollection(@PathVariable Integer collectionId, @RequestBody CollectionItem item) {
        Collection collection = collectionService.findByIdCollection(collectionId);
        if (collection != null) {
            collectionService.addItem(item, collectionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
@PostMapping("/{collectionId}/comments")
public ResponseEntity<Comment> addCommentToCollection(@PathVariable Integer collectionId, @RequestBody Comment comment) {
    Collection collection = collectionService.findByIdCollection(collectionId);
    if (collection != null) {
        collectionService.addComment(comment, collectionId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping("/{collectionId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCollection(@PathVariable Integer collectionId, @PathVariable Integer itemId) {
        // Retrieve the collection item by collectionId and itemId
        CollectionItem collectionItem = collectionItemService.findByIdCollectionItem(itemId);

        // Check if the collection item exists
        if (collectionItem == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Remove the collection item from the collection
        collectionService.removeItem(collectionItem, collectionId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Add other endpoints for collection manipulation (e.g., adding/removing items, adding comments) if needed
}
