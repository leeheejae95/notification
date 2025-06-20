package com.fc.event;

import lombok.Data;

import java.time.Instant;

@Data
public class FollowEvent {
    private FollowEventType type; // 이벤트 종류 구분
    private Long userId;
    private Long targetUserId; // 이벤트를 받을 사람
    private Instant createdAt; // 엔제 팔로우 했는지
}
