package org.example.services;

import lombok.AllArgsConstructor;
import org.example.entities.*;
import org.example.repositories.AppRoleRepository;
import org.example.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public long getTotal() {
        return userRepository.count();
    }@Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByIdUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUserName(name);
    }
    @Override
    public User addUser(String username, String email, String password, String confirmPassword) throws RuntimeException{
        User user = findByUserName(username);
        if (user != null) throw new RuntimeException("exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("password mismatch");
        user = new User(username, email, passwordEncoder.encode(password));
        user.getCollections().add(new Collection("Completed", CollectionType.Completed, user, "completed.jpg","Congratulations on finishing these captivating reads! This collection features the books that you have successfully completed. Reflect on your literary journey and consider sharing your thoughts with others."));
        user.getCollections().add(new Collection("Reading", CollectionType.Reading, user, "reading.jpg", "Discover the books that you are currently engrossed in. Dive into captivating stories, intriguing mysteries, and thought-provoking narratives as you explore the titles in this collection."));
        user.getCollections().add(new Collection("Want to Read", CollectionType.WantToRead, user, "wantToRead.jpg","This collection includes books that you are eager to read in the near future. Explore a curated selection of captivating titles and start building your reading list!"));
        user.getRankings().add(new Ranking(user, RankingPeriod.Week));
        user.getRankings().add(new Ranking(user, RankingPeriod.Month));
        user.getRankings().add(new Ranking(user, RankingPeriod.Year));
        userRepository.save(user);
        return addRoleToUser(username,"USER");
    }
    @Override
    public User addAdmin(String username, String email, String password, String confirmPassword) throws RuntimeException{
        User user = findByUserName(username);
        if (user != null) throw new RuntimeException("exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("password mismatch");
        user = new User(username, email, passwordEncoder.encode(password));
        userRepository.save(user);
        return addRoleToUser(username,"Admin");
    }

    @Override
    public User addRoleToUser(String username, String role) {
        User user = findByUserName(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().add(appRole);
        return userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        User user = userRepository.findByUserName(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().remove(appRole);
        userRepository.save(user);
    }

    @Override
    public void DeleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        for (Comment comment : user.getComments()) {
            comment.setUser(null);
            commentRepository.save(comment);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Integer id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setPasswordHash(updatedUser.getPasswordHash());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setProfilePicture(updatedUser.getProfilePicture());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public void getNotification(Notification n, Integer userid) {
        User user = userRepository.findById(userid).get();
        user.getNotifications().add(n);
        userRepository.save(user);
    }
    @Override
    public void addToFavorites(Collection c) {
        User user = getCurrentUser();
        user.getFavorites().add(c);
        userRepository.save(user);
        rankingService.updateUserScore(c.getOwner(), 10);
        rankingService.updateUserScore(user, 3);
    }
    @Override
    public void removeFromFavorites(Collection c) {
        User user = getCurrentUser();
        user.getFavorites().remove(c);
        userRepository.save(user);
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            return findByUserName(authentication.getName());
        } else
            return null;
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByUserNameContainingIgnoreCase(name);
    }
}
