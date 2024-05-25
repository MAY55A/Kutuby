package org.example.services;

import org.example.entities.Collection;
import org.example.entities.Notification;
import org.example.entities.User;

import java.util.List;

public interface IUserService  {

    long getTotal();

    public List<User> findAll();
    public User findByIdUser(Integer id);
    public User findByUserName(String name);
    public User addUser(String username, String password, String email, String confirmPassword) throws Exception;
    User addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    public void DeleteUser(Integer id);
    public User updateUser(Integer id , User user);


    void getNotification(Notification n, Integer userid);

    void addToFavorites(Collection c);

    void removeFromFavorites(Collection c);

    User getCurrentUser();
}
