package org.example.controllers;

import org.example.entities.*;

import org.example.services.IBookService;
import org.example.services.ICollectionItemService;
import org.example.services.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collections")
public class CollectionController {


    private final ICollectionService collectionService;
    private final ICollectionItemService collectionItemService;
    private final IBookService bookService;
    private final ProfileController profileController;

    @Autowired
    public CollectionController(ICollectionService collectionService, IBookService bookService, ICollectionItemService collectionItemService, IBookService bookService1, ProfileController profileController) {
        this.collectionService = collectionService;
        this.collectionItemService = collectionItemService;
        this.bookService = bookService1;
        this.profileController = profileController;
    }


    @GetMapping("/all")
    public String getAllCollections(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        return "Guest/collections";
    }

    @GetMapping("/{id}")
    public String getCollectionById(@PathVariable Integer id, Model model) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null) {
            collectionService.viewCollection(collection);
            model.addAttribute("collection", collection);
            model.addAttribute("user", profileController.getCurrentUser());
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
    @GetMapping("/add/{id}")
    public String viewAddPage(@PathVariable Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        User user = profileController.getCurrentUser();
        CollectionItem existingItem = collectionItemService.findByUserAndBook(user, book);
        if (existingItem != null) {
            model.addAttribute("item", existingItem);
        } else {
            CollectionItem newItem = new CollectionItem();
            newItem.setBook(book);
            newItem.setCreator(user);
            model.addAttribute("item", newItem);
        }
        return "User/addToCollection";
    }
    @PostMapping("/add")
    public String addItemToCollection(Model model, @RequestParam("bookId") Integer bookId, @RequestParam(value = "rating", required = false) String rating, @RequestParam(value = "progress", required = false) String progress, @RequestParam(value = "startedAt", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startedAt, @RequestParam(value = "finishedAT", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date finishedAt, @RequestParam("collId") Integer collId){
        CollectionItem item = new CollectionItem();
        Book book = bookService.findByIdBook(bookId);
        User user = profileController.getCurrentUser();
        item.setCreator(user);
        item.setBook(book);
        item.setStartedReadingAt(startedAt);
        item.setFinishedReadingAt(finishedAt);
        model.addAttribute("item", item);
        if (rating != null) {
            try {
                if (Integer.parseInt(rating) < 1 || Integer.parseInt(rating) > 10) {
                    model.addAttribute("message", "Rating must be between 1 and 10 !");
                    return "User/addToCollection";
                } else {
                    item.setRating(Short.parseShort(rating));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Rating must be a number !");
                return "User/addToCollection";
            }
        }
        if (progress != null) {
            try {
                if (Integer.parseInt(progress) < 0 || Integer.parseInt(progress) > 100) {
                    model.addAttribute("message", "Reading progress must be between 0 and 100");
                    return "User/addToCollection";
                } else {
                    item.setReadingProgress(Short.parseShort(progress));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Reading progress must be a number !");
                return "User/addToCollection";
            }
        }
        collectionService.addItem(item, collId);
        return "redirect:/books";
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
