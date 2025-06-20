package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationSaveService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void insert(Notification notification) {
        Notification result = notificationRepository.insert(notification);
        log.info("insert : {}", result);
    }

    public void upsert(Notification notification) {
        Notification result = notificationRepository.save(notification);
        log.info("upserted: {}", result);
    }
}
