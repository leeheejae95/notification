package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import com.fc.service.dto.GetUserNotificationsByPivotResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationListService {
    private static final int PAGE_SIZE = 3;
    private final NotificationRepository notificationRepository;

    // 목록 조회 : Pivot(기준점 : occurredAt, size) 방식
    public GetUserNotificationsByPivotResult getUserNotificationsByPivot(Long userId, Instant occurredAt) {
        Slice<Notification> result;
        if(occurredAt == null) {
            result = notificationRepository.findAllByUserIdOrderByOccurredAtDesc(userId, PageRequest.of(0,PAGE_SIZE));
        } else {
            result =  notificationRepository.findAllByUserIdAndOccurredAtLessThanOrderByOccurredAtDesc(userId, occurredAt, PageRequest.of(0, PAGE_SIZE));
        }

        return new GetUserNotificationsByPivotResult(
                result.toList(),
                result.hasNext()
        );
    }
}
