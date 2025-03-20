package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.job.JobOutputV1;
import com.example.JobWebsite.dto.job.JobOutputV3;
import com.example.JobWebsite.service.RecruiterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/recruiter")
public class RecruiterController {
    private final RecruiterService recruiterService;

    @Operation(summary = "Lấy ra các job")
    @GetMapping
    public Page<JobOutputV1> getJobsBy(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                       @ParameterObject Pageable pageable) {
        return recruiterService.getJobsBy(accessToken, pageable);
    }

    @Operation(summary = "Xem job theo state")
    @GetMapping("/state")
    public Page<JobOutputV3> getJobsByState(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                            @ParameterObject Pageable pageable,
                                            @RequestParam String state) {
        return recruiterService.getJobsByState(accessToken, pageable, state);
    }

    @Operation(summary = "Chấp nhận ứng tuyển")
    @PostMapping("/accept")
    public ApiResponse<?> acceptApplication(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                         @RequestParam Long recruiterJobId) {
        return recruiterService.acceptApplication(accessToken, recruiterJobId);
    }

    @Operation(summary = "Chấp nhận ứng tuyển")
    @PostMapping("/reject")
    public ApiResponse<?> rejectApplication(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                  @RequestParam Long recruiterJobId) {
        return recruiterService.rejectApplication(accessToken, recruiterJobId);
    }
}
