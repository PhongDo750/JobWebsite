package com.example.JobWebsite.repository;

import com.example.JobWebsite.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("SELECT n from NotificationEntity n where n.userId = :userId ORDER BY n.createAt DESC")
    Page<NotificationEntity> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    //SELECT COUNT(*)
    //FROM tbl_notification
    //WHERE user_id = userId AND has_seen = false;
    int countAllByUserIdAndHasSeenIsFalse(Long userId);

    void deleteAllByUserIdOrInteractId(Long userId, Long interactId);

    void deleteAllByJobId(Long jobId);
}
