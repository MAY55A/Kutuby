package org.example.services;


import org.example.entities.AppRole;
import org.example.entities.User;

public  interface AccountService {
    User addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    User loadUserByUsername(String username);
}
