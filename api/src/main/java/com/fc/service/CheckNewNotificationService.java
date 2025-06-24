package com.fc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CheckNewNotificationService {

    private final NotificationGetService notificationGetService;
    private final LastReadAtService lastReadAtService;
    public boolean checkNewNotification(long userId) {
        // lastReadAt vs latestUpdatedAt(가장 최근 업데이트 시간값)
        Instant latestUpdateAt = notificationGetService.getLatestUpdatedAt(userId);
        if(latestUpdateAt == null) {
            return false;
        }

        Instant lastReadAt = lastReadAtService.getLastReadAt(userId);
        // 단 한번도 읽지 않음
        if(lastReadAt == null) {
            return true;
        }

        return latestUpdateAt.isAfter(lastReadAt); // 업데이트 시간이 최근이면 ture리턴 ( 새로운 알림이 있는 경우 )
    }
}
