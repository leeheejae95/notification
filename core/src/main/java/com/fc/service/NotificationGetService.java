package com.fc.service;

import com.fc.domain.Notification;
import com.fc.repository.NotificationRepository;
import com.fc.code.NotificationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Component
public class NotificationGetService {

    @Autowired
    NotificationRepository notificationRepository;

    public Optional<Notification> getNotificationByTypeAndCommentId(NotificationType type, Long commentId) { // 알림조회?? 댓글조회아님?
        return notificationRepository.findByTypeAndCommentId(type,commentId);
    }

    // 좋아요를 누른 게시글 가져오기
    public Optional<Notification> getNotificationByTypeAndPostId(NotificationType type, Long postId) {
        return notificationRepository.findByTypeAndPostId(type, postId);
    }

    public Optional<Notification> getNotificationByTypeAndUserIdAndFollowerId(NotificationType type, Long userIdId, Long followerId) {
        return notificationRepository.findByTypeAndUserIdAndFollowerId(type, userIdId, followerId);
    }

    public Instant getLatestUpdatedAt(Long userId) {
        Optional<Notification> notification = notificationRepository.findFirstByUserIdOrderByLastUpdateAtDesc(userId);

        if(notification.isEmpty()) { // 알림이 없을 경우 null
            return  null;
        }

        return notification.get().getLastUpdateAt();
    }
}
