package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationListService {
    private static final int PAGE_SIZE = 20;
    private final NotificationRepository notificationRepository;

    // 목록 조회 : Pivot(기준점 : occurredAt, size) 방식
    public Slice<Notification> getUserNotificationsByPivot(Long userId, Instant occurredAt) {
        if(occurredAt == null) {
            return notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0,PAGE_SIZE));
        } else {
            return notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }
    }
}
