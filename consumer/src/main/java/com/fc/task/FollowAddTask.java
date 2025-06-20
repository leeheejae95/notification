package com.fc.task;

import com.fc.domain.FollowNotification;
import com.fc.util.NotificationIdGenerator;
import com.fc.service.NotificationSaveService;
import com.fc.code.NotificationType;
import com.fc.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowAddTask {

    private final NotificationSaveService notificationSaveService;

    public void processEvent(FollowEvent event) {
        if(event.getTargetUserId().equals(event.getUserId())) {
            log.error("targetUserId and userId cannot be the same");
            return;
        }

        notificationSaveService.upsert(createFollowerNotification(event));
    }

    private static FollowNotification createFollowerNotification(FollowEvent event) {
        Instant now = Instant.now();
        return new FollowNotification (
                NotificationIdGenerator.generate(),
                event.getTargetUserId(), // 이벤트 받는 사람
                NotificationType.FOLLOW,
                event.getCreatedAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                event.getUserId() // 팔로우 한 사용자
        );
    }
}
