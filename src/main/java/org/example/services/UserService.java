package org.example.services;

import org.example.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
@Autowired
    public UserService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByIdUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> findByUserName(String name) {
        return (List<User>) userRepository.findByUserName(name);
    }

    @Override
    public User addUser(User user) {
        user.getCollections().add(new Collection("Completed", CollectionType.Completed));
        user.getCollections().add(new Collection("Reading", CollectionType.Reading));
        user.getCollections().add(new Collection("Want to Read", CollectionType.WantToRead));
        user.getRankings().add(new Ranking(RankingPeriod.Week));
        user.getRankings().add(new Ranking(RankingPeriod.Month));
        user.getRankings().add(new Ranking(RankingPeriod.Year));
        return userRepository.save(user);
    }

    @Override
    public void DeleteUser(Integer id) {
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

    public void getNotification(Notification n, Integer userid) {
        User user = userRepository.findById(userid).get();
        user.getNotifications().add(n);
        userRepository.save(user);
    }
    public void addToFavourites(Collection c, Integer userid) {
        User user = userRepository.findById(userid).get();
        user.getFavourites().add(c);
        userRepository.save(user);
    }
}
