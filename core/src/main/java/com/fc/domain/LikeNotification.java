package com.fc.domain;

import com.fc.code.NotificationType;
import lombok.Getter;
import org.springframework.data.annotation.TypeAlias;

import java.time.Instant;
import java.util.List;

@Getter
@TypeAlias("LikeNotification")
public class LikeNotification extends Notification{// 좋아요 알림을 저장하기 위한 도메인 객체

    private final Long postId;
    private final List<Long> likerIds;

    public LikeNotification(String id, Long userId, NotificationType type, Instant occurredAt, Instant createdAt, Instant lastUpdateAt, Instant deletedAt, Long postId, List<Long> likerIds) {
        super(id, userId, type, occurredAt, createdAt, lastUpdateAt, deletedAt);
        this.postId = postId;
        this.likerIds = likerIds;
    }

    public void addLiker(Long likerId, Instant occurredAt, Instant now, Instant retention) {
        this.likerIds.add(likerId);
        this.setOccurredAt(occurredAt); // 신규이벤트 발생으로 업데이트
        this.setLastUpdateAt(now);
        this.setDeletedAt(retention);
    }

    public void removeLike(Long userId, Instant now) {
        this.likerIds.remove(userId);
        this.setLastUpdateAt(now);
    }
}
