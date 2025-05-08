package com.example.JobWebsite.repository;

import com.example.JobWebsite.entity.JobLikeMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobLikeMapRepository extends JpaRepository<JobLikeMapEntity, Long> {
    void deleteByUserIdAndJobId(Long userId, Long jobId);

    List<JobLikeMapEntity> findAllByUserId(Long userId);

    //SELECT EXISTS (
    //    SELECT 1
    //    FROM tbl_job_like_map
    //    WHERE user_id = userId AND job_id = jobId
    //);
    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    void deleteAllByUserId(Long userId);

    void deleteAllByJobIdIn(List<Long> jobIds);

    void deleteAllByJobId(Long jobId);
}
