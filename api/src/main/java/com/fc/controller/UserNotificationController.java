package com.fc.controller;

import com.fc.response.UserNotificationListResponse;
import com.fc.service.GetUserNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/user-notifications")
public class UserNotificationController implements UserNotificationListControllerSpec{

    private final GetUserNotificationsService getUserNotificationService;
    @Override
    @GetMapping("/{userId}")
    public UserNotificationListResponse getNotifications(
            @PathVariable(value = "userId") Long userId,
            @RequestParam(value = "pivot", required = false) Instant pivot
    ) {
        return UserNotificationListResponse.of(
                getUserNotificationService.getUserNotificationsByPivot(userId, pivot)
        );
    }
}
