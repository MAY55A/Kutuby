package org.example.services;

import org.example.entities.User;

import java.util.List;

public interface IUserService  {

    public List<User> findAll();
    public User findByIdUser(Integer id);
    public User findByUserName(String name);


    public List<User> findAdmins();

    public User addUser(String username, String password, String email, String confirmPassword) throws Exception;

    User addRoleToUser(String username, String role);

    void removeRoleFromUser(String username, String role);

    public void DeleteUser(Integer id);
    public User updateUser(Integer id , User user);


}
