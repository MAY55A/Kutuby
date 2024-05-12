package org.example.services;

import org.example.entities.Notification;
import org.example.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findByIdNotification(Integer id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        return notificationOptional.orElse(null);
    }

    @Override
    public Notification addNotification(Notification no) {
        return notificationRepository.save(no);
    }

    @Override
    public void deleteNotification(Integer id) { // Updated method signature
        notificationRepository.deleteById(id);
    }
    @Override
    public Notification updateNotification(Integer id, Notification no) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification existingNotification = notificationOptional.get();
            existingNotification.setRead(no.isRead());
            existingNotification.setContent(no.getContent());
            existingNotification.setSentAt(no.getSentAt());
            return notificationRepository.save(existingNotification);
        }
        return null;
    }
}
