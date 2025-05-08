package com.example.JobWebsite.repository;

import com.example.JobWebsite.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //SELECT EXISTS (
    //    SELECT 1
    //    FROM tbl_user
    //    WHERE username = 'phong'
    //);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    //SELECT *
    //FROM tbl_user
    //WHERE username = 'phong'
    //LIMIT 1;
    UserEntity findByUsername(String username);

    //SELECT *
    //FROM tbl_user
    //WHERE id IN (1, 2, 3, 4);
    List<UserEntity> findAllByIdIn(Set<Long> userIds);

    UserEntity findByGoogleId(String googleId);

    UserEntity findByEmail(String email);

    @Query("select u from UserEntity u where u.role != :role")
    Page<UserEntity> findAllUserWithoutAdmin(String role, Pageable pageable);
}
