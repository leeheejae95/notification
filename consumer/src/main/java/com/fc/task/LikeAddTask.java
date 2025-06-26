package com.fc.task;

import com.fc.client.PostClient;
import com.fc.domain.LikeNotification;
import com.fc.domain.Notification;
import com.fc.domain.Post;
import com.fc.event.LikeEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationSaveService;
import com.fc.util.NotificationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.fc.code.NotificationType.LIKE;

@Slf4j
@Component
public class LikeAddTask {

    @Autowired
    private PostClient postClient;

    @Autowired
    NotificationGetService notificationGetService;

    @Autowired
    NotificationSaveService notificationSaveService;

    public void processEvent(LikeEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if(post == null) {
            log.error("post null: {}", event.getPostId());
            return;
        }

        if(post.getUserId().equals(event.getUserId())) {
            return;
        }

        // likeNotification 1. 신규 생성 2. 업데이트
        Notification notification = createOrUpdateNotification(post, event);
        // likeNotification db 저장
        notificationSaveService.upsert(notification);
    }

    private Notification createOrUpdateNotification(Post post, LikeEvent event) {
        Optional<Notification> notification = notificationGetService.getNotificationByTypeAndPostId(LIKE, post.getId());
        Instant now = Instant.now();
        Instant retention = Instant.now().plus(90, ChronoUnit.DAYS);
        if(notification.isPresent()) {
            // 있는거 업데이트
            return updateNotification((LikeNotification) notification.get(), event, now, retention);
        } else {
            // 신규 생성
            return createNotification(post, event,now, retention);
        }
    }

    private Notification updateNotification(LikeNotification likeNotification, LikeEvent event, Instant now, Instant retention) {
        likeNotification.addLiker(
                event.getUserId(),
                event.getCreatedAt(),
                now,
                retention
        );

        return likeNotification;
    }

    private Notification createNotification(Post post, LikeEvent event, Instant now, Instant retention) {
        return new LikeNotification(
                NotificationIdGenerator.generate(),
                post.getUserId(), // 해당 포스트 주인에게 알림생성
                LIKE,
                event.getCreatedAt(),
                now,
                now,
                retention,
                post.getId(),
                List.of(event.getUserId())
        );
    }
}
