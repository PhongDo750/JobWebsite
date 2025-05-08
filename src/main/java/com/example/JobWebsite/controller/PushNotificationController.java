package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.service.PushNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/push-notification")
public class PushNotificationController {
    private final PushNotificationService pushNotificationService;

    @Operation(summary = "Lấy public key của server")
    @GetMapping
    public String getPublicServerKey() {
        return pushNotificationService.getPublicKey();
    }

    @Operation(summary = "Đăng ký nhận thông báo từ server")
    @PostMapping("/subscribe")
    public ApiResponse<?> subscribe(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                    @RequestBody Subscription subscription) {
        return pushNotificationService.subscribe(accessToken, subscription);
    }

    @Operation(summary = "Hủy đăng ký nhận thông báo")
    @DeleteMapping("/unsubscribe")
    public ApiResponse<?> unsubscribe(@RequestHeader(Common.AUTHORIZATION) String accessToken) {
        return pushNotificationService.unsubscribe(accessToken);
    }
}
