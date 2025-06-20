package com.fc.task;

import com.fc.client.PostClient;
import com.fc.code.NotificationType;
import com.fc.domain.Post;
import com.fc.event.CommentEvent;
import com.fc.service.NotificationGetService;
import com.fc.service.NotificationRemoveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class CommentRemoveTask {

    @Autowired
    PostClient postClient;

    @Autowired
    NotificationGetService notificationGetService;

    @Autowired
    NotificationRemoveService notificationRemoveService;

    public void processEvent(CommentEvent event) {
        Post post = postClient.getPost(event.getPostId());
        if(Objects.equals(post.getUserId(), event.getUserId())) {
            return;
        }

        notificationGetService.getNotificationByTypeAndCommentId(NotificationType.COMMENT, event.getCommentId())
                .ifPresent(
                        notification -> notificationRemoveService.deleteById(notification.getId())
                );
    }
}
