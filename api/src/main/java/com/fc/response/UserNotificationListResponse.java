package com.fc.response;

import com.fc.service.dto.GetUserNotificationsResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "유저 알림 목록 응답")
public class UserNotificationListResponse {
    @Schema(description = "알림목록")
    private List<UserNotificationResponse> notifications;

    @Schema(description = "다음 페이지 존재 여부")
    private boolean hasNext; // 다음페이지가 있는지

    @Schema(description = "다음 페이지 요청시 전달할 pivot 파라미터")
    private Instant pivot;

    public static UserNotificationListResponse of(GetUserNotificationsResult result) {
        // ConvertedNotification -> UserNotificationResponse로 변환 작업 필요
        // 다른 클래스로 쓰는 이유는 사용자에게 내려주는 응답은 많이 바뀔 여지가 있고
        // 서비스에 필드가 추가되고 유저에게 바로 내려가는 것도 위험
        List<UserNotificationResponse> userNotificationResponses = result.getNotifications().stream()
                //.map(UserNotificationResponse::of)
                .map(UserNotificationResponse::of)
                .toList();
        // pivot 생성
        // 다음 알림 목록 호출하기 위함인데 다음 알림 목록이 없으면 내려줄 필요 없음
        Instant pivot = (result.isHasNext() && !userNotificationResponses.isEmpty())? userNotificationResponses.getLast().getOccurredAt() : null;
        return new UserNotificationListResponse(
                userNotificationResponses,
                result.isHasNext(),
                pivot
        );
    }
}
