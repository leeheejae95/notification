package com.fc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckNewNotificationServiceTest {

    @Mock
    private NotificationGetService notificationGetService;

    @Mock
    private LastReadAtService lastReadAtService;

    @InjectMocks // 만든 mock들을 CheckNewNotificationService 안에 주입
    private CheckNewNotificationService checkNewNotificationService;

    @Test
    void notification_not_fount_false_return() {
        // 시나리오
        // 알림 자체가 하나도 없는 경우
        // latestUpdatedAt == null 이라고 가정
        // 이런 경우에는 "새 알림 없음" -> false 가 나와야 한다.
        long userId = 1L;

        boolean result = checkNewNotificationService.checkNewNotification(userId);

        assertFalse(result);
    }

    @Test
    void notification_one_notRead_true_return() {
        // 시나리오
        // 알림은 존재함 (latestUpdatedAt != null)
        // 하지만 사용자가 한 번도 읽은 적이 없음 (lastReadAt == null)
        // 이런 경우에는 "읽지 않은 알림이 있다" -> true 가 되어야 한다.
        long userId = 1L;
        Instant latestUpdatedAt = Instant.parse("2025-11-29T10:00:00Z");

        when(notificationGetService.getLatestUpdatedAt(anyLong()))
                .thenReturn(latestUpdatedAt);
        when(lastReadAtService.getLastReadAt(anyLong()))
                .thenReturn(null);

        // when
        boolean result = checkNewNotificationService.checkNewNotification(userId);

        // then
        assertTrue(result);
    }

    @Test
    void lastTime_prevUpdate_notification_true_return() {
        // 시나리오
        // 알림이 존재함
        // 사용자가 마지막으로 읽은 시간(lastReadAt)보다
        // 더 나중에 업데이트된 알림(latestUpdatedAt)이 있음
        // 즉, 읽은 이후에 새 알림이 도착한 상태 -> true 여야 함
        long userId = 1L;
        Instant latestUpdatedAt = Instant.parse("2025-01-01T10:00:00Z");
        Instant lastReadAt = Instant.parse("2025-01-01T09:00:00Z");

        when(notificationGetService.getLatestUpdatedAt(userId))
                .thenReturn(latestUpdatedAt);
        when(lastReadAtService.getLastReadAt(userId))
                .thenReturn(lastReadAt);

        // when
        boolean result = checkNewNotificationService.checkNewNotification(userId);

        // then
        assertTrue(result);
    }

    @Test
    void lastTime_more_recent_false_return() {
        // 시나리오
        // 알림이 존재하긴 하지만,
        // 사용자가 마지막으로 읽은 시간(lastReadAt)이
        // 최신 알림 시간(latestUpdatedAt)보다 "더 나중"인 경우
        // 즉, 사용자가 가장 마지막 알림까지 이미 다 읽은 상태 -> false 여야 함
        long userId = 1L;
        // 최신 알림 업데이트 시각은 10:00
        Instant latestUpdatedAt = Instant.parse("2025-01-01T10:00:00Z");
        // 사용자가 마지막으로 읽은 시각은 그보다 늦은 11:00
        Instant lastReadAt = Instant.parse("2025-01-01T11:00:00Z");

        when(notificationGetService.getLatestUpdatedAt(userId))
                .thenReturn(latestUpdatedAt);
        when(lastReadAtService.getLastReadAt(userId))
                .thenReturn(lastReadAt);

        // when
        boolean result = checkNewNotificationService.checkNewNotification(userId);

        // then
        assertFalse(result);
    }
}
