package com.fc.service;

import com.fc.domain.CommentNotification;
import com.fc.domain.FollowNotification;
import com.fc.domain.LikeNotification;
import com.fc.service.convert.CommentUserNotificationConvertor;
import com.fc.service.convert.FollowUserNotificationConvertor;
import com.fc.service.convert.LikeUserNotificationConvertor;
import com.fc.service.dto.ConvertedNotification;
import com.fc.service.dto.GetUserNotificationsByPivotResult;
import com.fc.service.dto.GetUserNotificationsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUserNotificationsService {

    private final NotificationListService notificationListService;
    private final CommentUserNotificationConvertor commentConvertor;
    private final LikeUserNotificationConvertor likeConvertor;
    private final FollowUserNotificationConvertor followConvertor;

    public GetUserNotificationsResult getUserNotificationsByPivot(Long userId, Instant pivot) {
        // 데이터가공
        // 알림목록 조회
        GetUserNotificationsByPivotResult result = notificationListService.getUserNotificationsByPivot(userId, pivot);

        // 알림목록을 순회 하면서 디비 알림 -> 사용자 알림으로 변환
        List<ConvertedNotification> convertedNotifications = result.getNotifications().stream()
                .map(notification -> switch (notification.getType()) {
                            case COMMENT -> commentConvertor.convert((CommentNotification) notification); // 사용자에게 내려줄 댓글 정보 변환
                            case LIKE -> likeConvertor.convert((LikeNotification) notification);
                            case FOLLOW -> followConvertor.convert((FollowNotification) notification);
        })
        .toList();

        return new GetUserNotificationsResult(
                convertedNotifications,
                result.isHasNext()
        );
    }
}
