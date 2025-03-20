package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.notification.NotificationDTO;
import com.example.JobWebsite.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ApiResponse<Page<NotificationDTO>> getNotifications(@ParameterObject Pageable pageable,
                                                               @RequestHeader(Common.AUTHORIZATION) String accessToken) {
        return notificationService.getNotifications(pageable, accessToken);
    }

    @GetMapping("/count")
    public ApiResponse<Integer> countNotificationsHasNotSeen(@RequestHeader(Common.AUTHORIZATION) String accessToken) {
        return notificationService.countNotificationsHasNotSeen(accessToken);
    }
}
