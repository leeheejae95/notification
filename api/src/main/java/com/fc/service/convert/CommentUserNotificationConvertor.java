package com.fc.service.convert;

import com.fc.client.PostClient;
import com.fc.client.UserClient;
import com.fc.domain.CommentNotification;
import com.fc.domain.Post;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedCommentNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUserNotificationConvertor {

    private final UserClient userClient;
    private final PostClient postClient;
    public ConvertedCommentNotification convert(CommentNotification notification) {
        User user = userClient.getUser(notification.getWriterId()); // 작성자 정보
        Post post = postClient.getPost(notification.getPostId()); // 게시글 정보

        return new ConvertedCommentNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdateAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getComment(),
                post.getImageUrl()
        );
    }
}
