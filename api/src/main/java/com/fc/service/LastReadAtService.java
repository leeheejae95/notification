package com.fc.service;

import com.fc.repository.NotificationReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LastReadAtService {

    private final NotificationReadRepository notificationReadRepository;

    public Instant setLastReadAt(long userId) {
        return notificationReadRepository.setLastReadAt(userId);
    }

    public Instant getLastReadAt(long userId) {
        return notificationReadRepository.getLastReadAt(userId);
    }
}
