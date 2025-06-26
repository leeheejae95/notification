package com.fc.controller;

import com.fc.response.SetLastReadAtResponse;
import com.fc.service.LastReadAtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user-notifications")
public class NotificationReadController implements NotificationReadControllerSpec{

    private final LastReadAtService lastReadAtService;
    @Override
    @PutMapping("/{userId}/read")
    public SetLastReadAtResponse setLastReadAt(@PathVariable(value = "userId") Long userId) {
        Instant lastReadAt = lastReadAtService.setLastReadAt(userId);
        return new SetLastReadAtResponse(lastReadAt);
    }
}
