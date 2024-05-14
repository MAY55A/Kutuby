package org.example.services;


import org.example.entities.AppRole;
import org.example.entities.AppUser;

public  interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUSer(String username,String role);
    void removeRoleFRomUSer(String username,String role);
    AppUser loadUserByUsername(String username);
}
