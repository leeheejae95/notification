package com.fc.service.convert;

import com.fc.client.PostClient;
import com.fc.client.UserClient;
import com.fc.domain.LikeNotification;
import com.fc.domain.Post;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedLikeNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeUserNotificationConvertor {

    private final UserClient userClient;
    private final PostClient postClient;
    public ConvertedLikeNotification convert(LikeNotification notification) {
        User user = userClient.getUser(notification.getLikerIds().getLast()); // 작성자 정보
        Post post = postClient.getPost(notification.getPostId()); // 게시글 정보

        return new ConvertedLikeNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdateAt(),
                user.getName(),
                user.getProfileImageUrl(),
                notification.getLikerIds().size(),
                post.getImageUrl()
        );
    }
}
