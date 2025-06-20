package com.fc.task;

import com.fc.code.NotificationType;
import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.event.LikeEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import com.fc.service.NotificationSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class LikeRemoveTask {

    @Autowired
    NotificationGetService notificationGetService; // post에 좋아요가 있는지 조회

    @Autowired
    NotificationRemoveService notificationRemoveService;

    @Autowired
    NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {
        Optional<Notification> notification = notificationGetService.getNotificationByTypeAndPostId(NotificationType.LIKE, event.getPostId());

        if(notification.isEmpty()) {
            log.error("No Notification with postId: {}", event.getPostId());
            return;
        }

        LikeNotification likeNotification = (LikeNotification) notification.get();
        removeLikerAndUpdateNotification(likeNotification, event);

    }

    private void removeLikerAndUpdateNotification(LikeNotification notification, LikeEvent event) {
        // likers에서 event에 userId제거 1. likers가 비었으면 알림 삭제 2. 남아 있으면 알림 업데이트
        notification.removeLike(event.getUserId(), Instant.now());

        if(notification.getLikerIds().isEmpty()) {
            notificationRemoveService.deleteById(notification.getId());
        } else {
            notificationSaveService.upsert(notification);
        }
    }
}
