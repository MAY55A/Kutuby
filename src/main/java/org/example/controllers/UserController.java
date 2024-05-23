package org.example.controllers;

import org.example.entities.Notification;
import org.example.entities.User;
import org.example.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "Admin/users";
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id, Model model) {
        User user = userService.findByIdUser(id);
        return user;
    }

    @PostMapping
    public  String addUser(@RequestBody String name, String password1, String email, String password2, Model model) {
        try {
            User addedUser = userService.addUser(name, password1, email, password2);
            model.addAttribute("user", addedUser);
            return "User/user_account";
        } catch (Exception e){
            if(e.getMessage().equals("exists"))
                model.addAttribute("msg", "User already exits !");
            else
                model.addAttribute("msg", "The confirmation password does not match !");
            return "Guest/sign_up";
        }
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody User user, Model model) {
        User updatedUser = userService.updateUser(id, user);
        model.addAttribute("user", updatedUser);
        if (updatedUser != null) {
            return "User/user_account";
        } else {
            return "Errors/not_found";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.DeleteUser(id);
        return "Guest/home";
    }

    /*@GetMapping("/")
    public String getUserDetails(@RequestParam("id") Integer id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        User user = userService.findByIdUser(id);
        if (user != null) {
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("user", user);
            userDetails.put("collections", user.getCollections());
            userDetails.put("comments", user.getComments());
            userDetails.put("rankings", user.getRankings());
            userDetails.put("notifications", user.getNotifications());
            model.addAttribute("user", userDetails);
            if (loggedInUsername.equals(user.getUserName())) {
                // User is viewing their own profile
                model.addAttribute("myProfile", true);
            } else {
                // User is viewing another user's profile
                model.addAttribute("myProfile", false);
            }
            return "User/user_account";
        } else {
            return "Errors/not_found";
        }
    }
    */
}