package com.fc.domain;

import com.fc.code.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Document("notifications") // mongodb에 어떤 컬렉션을 넣을거에요 명시
public abstract class Notification {
    @Field(targetType = FieldType.STRING)
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant occurredAt; // 알림 대상인 실제 이벤트가 발생한 시간
    private Instant createdAt;
    private Instant lastUpdateAt;
    private Instant deletedAt; // 알림이 삭제된 시간
}
