package com.fc.event;

import lombok.Data;

import java.time.Instant;

@Data
public class LikeEvent {
    private LikeEventType type; // 이벤트 종류 구분
    private Long postId;
    private Long userId;
    private Instant createdAt; // 엔제 좋아요를 눌렀는지
}
