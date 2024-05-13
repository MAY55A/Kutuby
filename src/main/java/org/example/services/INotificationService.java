package org.example.services;

import org.example.entities.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> findAll();
    Notification findByIdNotification(Integer id);
    Notification addNotification(Notification no);
    void deleteNotification(Integer id); // Update parameter to accept Integer ID
    Notification updateNotification(Integer id, Notification no);
}
