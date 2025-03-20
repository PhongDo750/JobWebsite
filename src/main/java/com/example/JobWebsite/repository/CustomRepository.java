package com.example.JobWebsite.repository;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.entity.JobEntity;
import com.example.JobWebsite.entity.RecruiterJobMapEntity;
import com.example.JobWebsite.entity.UserEntity;
import com.example.JobWebsite.entity.UserJobMapEntity;
import com.example.JobWebsite.exceptionhandler.AppException;
import com.example.JobWebsite.exceptionhandler.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomRepository {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final RecruiterJobMapRepository recruiterJobMapRepository;
    private final UserJobMapRepository userJobMapRepository;

    public UserEntity getUserBy(Long userId){
        return userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.RECORD_NOT_FOUND)
        );
    }

    public JobEntity getJobBy(Long jobId){
        return jobRepository.findById(jobId).orElseThrow(
                () -> new RuntimeException(Common.RECORD_NOT_FOUND)
        );
    }

    public RecruiterJobMapEntity getRecruiterJobMap(Long recruiterJobId){
        return recruiterJobMapRepository.findById(recruiterJobId).orElseThrow(
                () -> new RuntimeException(Common.RECORD_NOT_FOUND)
        );
    }

    public UserJobMapEntity getUserJobMap(Long userJobId){
        return userJobMapRepository.findById(userJobId).orElseThrow(
                () -> new RuntimeException(Common.RECORD_NOT_FOUND)
        );
    }
}
