package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.job.JobInput;
import com.example.JobWebsite.dto.job.JobOutputV1;
import com.example.JobWebsite.dto.job.JobOutputV2;
import com.example.JobWebsite.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/job")
public class JobController {
    private final JobService jobService;

    @Operation(summary = "Đăng tuyển")
    @PostMapping("/create")
    public ApiResponse<?> createJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                    @RequestBody @Valid JobInput jobInput) {
        return jobService.createJob(accessToken, jobInput);
    }

    @Operation(summary = "Update Job")
    @PostMapping("/update")
    public ApiResponse<?> updateJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                          @RequestBody @Valid JobInput jobInput,
                          @RequestParam Long jobId) {
        jobService.updateJob(accessToken, jobInput, jobId);
        return ApiResponse.builder()
                .code(200)
                .message("Thay đổi thông tin thành công")
                .build();
    }

    @Operation(summary = "Xóa tin tuyển dụng")
    @DeleteMapping("/delete")
    public ApiResponse<?> deleteJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                          @RequestParam Long jobId) {
        jobService.deleteJob(accessToken, jobId);
        return ApiResponse.builder()
                .code(200)
                .message("Xóa công việc thành công")
                .build();
    }

    @Operation(summary = "Tìm kiếm job")
    @GetMapping("/filter")
    public Page<JobOutputV1> filterJobs(@ParameterObject Pageable pageable,
                                        @RequestParam(required = false) String jobName,
                                        @RequestParam(required = false) String occupationName,
                                        @RequestParam(required = false) String experience,
                                        @RequestParam(required = false) String province,
                                        @RequestParam(required = false) String jobType,
                                        @RequestParam(required = false) String jobLevel,
                                        @RequestParam(required = false) Double minSalary,
                                        @RequestParam(required = false) Double maxSalary,
                                        @RequestParam(required = false) String educationLevel,
                                        @RequestHeader(name = "Authorization", required = false) String accessToken) {
        return jobService.filterJobs(
                pageable, jobName, occupationName, experience, province,
                jobType, jobLevel, minSalary, maxSalary, educationLevel, accessToken
        );
    }

    @Operation(summary = "Xem chi tiết job")
    @GetMapping("/descriptions")
    public ApiResponse<JobOutputV2> getDescriptionsJob(@RequestParam Long jobId,
                                                       @RequestHeader(name = Common.AUTHORIZATION, required = false) String accessToken) {
        return jobService.getDescriptionJob(jobId, accessToken);
    }

    @Operation(summary = "Xem job đã thích")
    @GetMapping("/liked")
    public Page<JobOutputV1> getLikedJob(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                                         @ParameterObject Pageable pageable) {
        return jobService.getLikedJobs(accessToken, pageable);
    }

    @Operation(summary = "Lấy job theo trạng thái")
    @GetMapping("/state")
    public Page<JobOutputV1> getJobsByState(@ParameterObject Pageable pageable,
                                            @RequestParam String state,
                                            @RequestHeader(Common.AUTHORIZATION) String accessToken) {
        return jobService.getJobsByState(pageable, state, accessToken);
    }

    @GetMapping("/get-top-job")
    public ApiResponse<List<JobOutputV1>> getTopApplication(@RequestParam Integer quantity) {
        return jobService.getMostJobApplication(quantity);
    }
}
