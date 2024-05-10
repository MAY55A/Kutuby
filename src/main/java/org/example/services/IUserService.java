package org.example.services;

import org.example.entities.User;

import java.util.List;

public interface IUserService  {

    public List<User> findAll();
    public User findByIdUser(Integer id);
     public List<User> findByUserName(String name);



    public User addUser(User d);
    public void DeleteUser(Integer id);
    public User updateUser(Integer id , User user);


}
