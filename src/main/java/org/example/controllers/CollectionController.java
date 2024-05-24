package org.example.controllers;

import org.example.entities.*;

import org.example.services.IBookService;
import org.example.services.ICollectionItemService;
import org.example.services.ICollectionService;
import org.example.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final IBookService bookService;
    private final IUserService userService;

    @Autowired
    public CollectionController(ICollectionService collectionService, IBookService bookService, ICollectionItemService collectionItemService, IBookService bookService1, ProfileController profileController, IUserService userService) {
        this.collectionService = collectionService;
        this.collectionItemService = collectionItemService;
        this.bookService = bookService1;
        this.userService = userService;
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
                    return "Errors/not_found";
            }
        }

        model.addAttribute("collections", collections);
        return "Guest/collections";
    }


    @GetMapping("/{id}")
    public String getCollectionById(@PathVariable Integer id, Model model) {
        Collection collection = collectionService.findByIdCollection(id);
        User user = userService.getCurrentUser();
        if (collection != null && (collection.getVisibility() != Visibility.Private || user == collection.getOwner())) {
            collectionService.viewCollection(collection);
            model.addAttribute("collection", collection);
            model.addAttribute("user", user);
            return "Guest/collection";
        } else if(collection == null)
            return "Errors/not_found";
        else
            return "Errors/unauthorised";
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public String addCollection(@RequestBody Collection collection) {
        Collection addedCollection = collectionService.addCollection(collection);
        return "redirect:profile/collections";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public String updateCollection(@PathVariable Integer id, @RequestBody Collection collection) {
        if (userService.getCurrentUser() == collection.getOwner()) {
            Collection updatedCollection = collectionService.updateCollection(id, collection);
            if(updatedCollection != null)
                return "redirect:profile/collections";
            return "Errors/not_found";
        }
        return "Errors/unauthorised";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public String deleteCollection(@PathVariable Integer id) {
        Collection collection = collectionService.findByIdCollection(id);
        if (collection != null && userService.getCurrentUser() == collection.getOwner()) {
            collectionService.DeleteCollection(id);
            return "redirect:profile/collections";
        }else if(collection == null)
            return "Errors/not_found";
        else
            return "Errors/unauthorised";
    }
    @GetMapping("/add/{id}")
    @PreAuthorize("hasRole('USER')")
    public String viewAddItemPage(@PathVariable Integer id, Model model) {
        Book book = bookService.findByIdBook(id);
        User user = userService.getCurrentUser();
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
    @PreAuthorize("hasRole('USER')")
    public String addItemToCollection(Model model, @RequestParam("bookId") Integer bookId, @RequestParam(value = "rating", required = false) String rating, @RequestParam(value = "progress", required = false) short progress, @RequestParam(value = "startedAt", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startedAt, @RequestParam(value = "finishedAT", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date finishedAt, @RequestParam("collId") Integer collId){
        CollectionItem item = new CollectionItem();
        Book book = bookService.findByIdBook(bookId);
        User user = userService.getCurrentUser();
        item.setCreator(user);
        item.setBook(book);
        item.setStartedReadingAt(startedAt);
        item.setFinishedReadingAt(finishedAt);
        model.addAttribute("item", item);
        if (!rating.equals("-")) {
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
        if (progress < 0 || progress > 100) {
            model.addAttribute("message", "Reading progress must be between 0 and 100");
            return "User/addToCollection";
        } else {
            item.setReadingProgress(progress);
        }
        collectionService.addItem(item, collId);
        return "redirect:/books";
    }
@PostMapping("/{collectionId}/comments")
@PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    public String removeItemFromCollection(@PathVariable Integer collectionId, @PathVariable Integer itemId) {
        CollectionItem collectionItem = collectionItemService.findByIdCollectionItem(itemId);
        if (collectionItem == null) {
            return "Errors/not_found";
        }
        if (userService.getCurrentUser() == collectionItem.getCreator())
            collectionService.removeItem(collectionItem, collectionId);

        return "Errors/unauthorised";
    }
}
