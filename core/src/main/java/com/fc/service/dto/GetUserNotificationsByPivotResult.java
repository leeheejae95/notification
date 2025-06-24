package com.fc.service.dto;

import com.fc.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class GetUserNotificationsByPivotResult {

    private List<Notification> notifications;
    private boolean hasNext;
}
