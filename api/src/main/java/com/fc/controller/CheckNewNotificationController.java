package com.fc.controller;

import com.fc.response.CheckNewNotificationResponse;
import com.fc.service.CheckNewNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
public class CheckNewNotificationController implements  CheckNewNotificationControllerSpec{

    private final CheckNewNotificationService checkNewNotificationService;
    @Override
    @GetMapping("/{userId}/new")
    public CheckNewNotificationResponse checkNew(@PathVariable(value = "userId") Long userId) {
        boolean hasNew = checkNewNotificationService.checkNewNotification(userId);

        return new CheckNewNotificationResponse(hasNew);
    }
}
