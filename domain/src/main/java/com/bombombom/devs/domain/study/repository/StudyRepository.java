package com.bombombom.devs.domain.study.repository;

import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.Study;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudyRepository {

    Optional<Study> findStudyWithUsersById(Long id);

    Study save(Study study);

    Page<Study> findAll(Pageable pageable);

    List<Study> findStudyHavingRoundToStartWithUsers(LocalDate localDate);
}
