package com.fc.service.dto;

import com.fc.code.NotificationType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ConvertedFollowNotification extends ConvertedNotification{
    private final String userName;
    private final String userProfileImageUrl;
    private final boolean isFollowing;
    public ConvertedFollowNotification(String id, NotificationType type, Instant occurredAt, Instant lastUpdateAt,
                                       String userName, String userProfileImageUrl, boolean isFollowing) {
        super(id, type, occurredAt, lastUpdateAt);
        this.userName = userName;
        this.userProfileImageUrl = userProfileImageUrl;
        this.isFollowing = isFollowing;
    }
}
