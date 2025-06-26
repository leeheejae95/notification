package com.fc.service.dto;

import com.fc.code.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
@Getter
public class ConvertedLikeNotification extends ConvertedNotification{
    private final String userName;
    private final String userProfileImageUrl;
    private final long userCount;
    private final String postImageUrl;
    public ConvertedLikeNotification(String id, NotificationType type, Instant occurredAt, Instant lastUpdateAt, String userName,
                                     String userProfileImageUrl, long userCount, String postImageUrl) {
        super(id, type, occurredAt, lastUpdateAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.userCount = userCount;
        this.postImageUrl = postImageUrl;
    }
}
