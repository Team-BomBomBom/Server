package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.mysql.study.entity.UserStudy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {

    boolean existsByUserIdAndStudyId(Long userId, Long studyId);

    @Query("select us from UserStudy us join fetch us.user where us.study.id = :studyId")
    List<UserStudy> findByStudyId(Long studyId);
}

