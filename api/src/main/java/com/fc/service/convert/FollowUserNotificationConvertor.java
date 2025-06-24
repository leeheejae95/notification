package com.fc.service.convert;

import com.fc.client.UserClient;
import com.fc.domain.FollowNotification;
import com.fc.domain.User;
import com.fc.service.dto.ConvertedFollowNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUserNotificationConvertor {

    private final UserClient userClient;
    public ConvertedFollowNotification convert(FollowNotification notification) {
        User user = userClient.getUser(notification.getFollowerId()); // 작성자 정보
        boolean isFollowing = userClient.getIsFollowing(notification.getUserId(), notification.getFollowerId());
        return new ConvertedFollowNotification(
                notification.getId(),
                notification.getType(),
                notification.getOccurredAt(),
                notification.getLastUpdateAt(),
                user.getName(),
                user.getProfileImageUrl(),
                isFollowing
        );
    }
}
