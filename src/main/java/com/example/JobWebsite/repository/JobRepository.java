package com.example.JobWebsite.repository;

import com.example.JobWebsite.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long>, JpaSpecificationExecutor<JobEntity> {
    //SELECT *
    //FROM tbl_job
    //WHERE user_id = userId
    //ORDER BY created_at DESC
    //LIMIT 10 OFFSET 20; => page = 2, size = 10, offset page bo qua
    Page<JobEntity> findAllByUserId(Long userId, Pageable pageable);

    //SELECT *
    //FROM tbl_job
    //WHERE id IN jobIds
    //ORDER BY created_at DESC
    //LIMIT 10 OFFSET 0;
    Page<JobEntity> findAllByIdIn(List<Long> jobIds, Pageable pageable);

    @Query("SELECT j FROM JobEntity j WHERE j.id IN :jobIds")
    List<JobEntity> findAllByIdInWithoutPaging(@Param("jobIds") List<Long> jobIds);

    @Query("SELECT COUNT(u) FROM JobEntity u " +
            "WHERE EXTRACT(YEAR FROM u.createAt) = :year " +
            "AND EXTRACT(MONTH FROM u.createAt) = :month")
    int countJobsPostedInMonth(@Param("year") int year, @Param("month") int month);


    @Query("SELECT u FROM JobEntity u " +
            "WHERE EXTRACT(YEAR FROM u.createAt) = :year " +
            "AND EXTRACT(MONTH FROM u.createAt) = :month")
    Page<JobEntity> findJobsPostedInMonth(@Param("year") int year, @Param("month") int month, Pageable pageable);

    @Query("SELECT j FROM JobEntity j WHERE j.userId = :userId")
    List<JobEntity> findAllByUserIdWithoutPaging(@Param("userId") Long userId);

    //DELETE FROM tbl_job
    //WHERE user_id = userId;
    void deleteAllByUserId(Long userId);

    List<JobEntity> findAllByIdIn(List<Long> jobIds);

}


