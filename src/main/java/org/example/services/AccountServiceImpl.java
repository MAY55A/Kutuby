package org.example.services;

import lombok.AllArgsConstructor;
import org.example.entities.User;
import org.example.entities.AppRole;
import org.example.repositories.AppRoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //nouveau methode pour genere un id aleatoir pour un nouveau user :
    private int generateUniqueId() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE);
    }
    @Override
    public User addNewUser(String username, String password, String email, String confirmPassword) {
        User user = userRepository.findByUserName(username);
        if (user != null) throw new RuntimeException("user déja existant");
        if (!password.equals(confirmPassword)) throw new RuntimeException("password incorrect");
        // Generate unique id
        int userId = generateUniqueId();
        user = User.builder()
                .id(userId)
                .userName(username)
                .passwordHash(passwordEncoder.encode(password))
                .build();
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("role déja existant");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        User user = userRepository.findByUserName(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        User user = userRepository.findByUserName(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().remove(appRole);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
