package com.bombombom.devs.domain.study.repository;

import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.Study;
import java.util.Optional;

public interface StudyRepository {

    Optional<Study> findStudyWithUsersById(Long id);

    Page<Study> findAllWithUserAndBook(Pageable pageable);

}
