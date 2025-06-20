package com.fc.task;

import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import com.fc.code.NotificationType;
import com.fc.event.FollowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowRemoveTask {

    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;

    public void processEvent(FollowEvent event) {
        notificationGetService.getNotificationByTypeAndUserIdAndFollowerId(NotificationType.FOLLOW, event.getTargetUserId(),
                        event.getUserId())
                .ifPresent(
                        notification -> notificationRemoveService.deleteById(notification.getId())
                );
    }
}
