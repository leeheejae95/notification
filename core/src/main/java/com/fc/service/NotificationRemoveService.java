package com.fc.service;

import com.fc.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationRemoveService {

    @Autowired
    NotificationRepository notificationRepository;

    public void deleteById(String id) {
        log.info("deleted : {}", id);
        notificationRepository.deleteById(id);
    }
}
