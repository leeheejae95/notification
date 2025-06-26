package com.fc.service.dto;

import com.fc.code.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
@Getter
@AllArgsConstructor
public abstract class ConvertedNotification {
    protected String id; // 알림 고유 ID
    protected NotificationType type;
    protected Instant occurredAt; // 알림 발생 시간
    protected Instant lastUpdateAt;
}
