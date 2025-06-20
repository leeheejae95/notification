package com.fc.event;

import lombok.Data;

@Data
public class CommentEvent {

    private CommentEventType type; // 이벤트 종류 구분
    private Long postId;
    private Long userId;
    private Long commentId;
}
