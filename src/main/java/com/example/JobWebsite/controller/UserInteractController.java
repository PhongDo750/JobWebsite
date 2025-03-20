package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.job.ApplyJobInput;
import com.example.JobWebsite.service.UserInteractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/interact")
public class UserInteractController {
    private final UserInteractService userInteractService;

    @Operation(summary = "Nộp đơn ứng tuyển")
    @PostMapping("/apply")
    public ApiResponse<?> applyJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                @RequestPart("job_application") @Valid String applyJobInputString,
                                @RequestPart(value = "file") MultipartFile filePDF) throws JsonProcessingException {
        ApplyJobInput applyJobInput;
        ObjectMapper objectMapper = new ObjectMapper();
        applyJobInput = objectMapper.readValue(applyJobInputString, ApplyJobInput.class);
        return userInteractService.applyJob(accessToken, applyJobInput, filePDF);
    }

    @Operation(summary = "like job")
    @PostMapping("/like")
    public void likeJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                        @RequestParam Long jobId) {
        userInteractService.likeJob(accessToken, jobId);
    }

    @Operation(summary = "remove like")
    @DeleteMapping("/remove-like")
    public void removeLikeJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                              @RequestParam Long jobId) {
        userInteractService.removeLikeJob(accessToken, jobId);
    }
}
