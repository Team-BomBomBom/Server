package com.bombombom.devs.domain.study.repository;

import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.vo.Pageable;
import java.util.Optional;

public interface StudyRepository {

    Optional<Study> findStudyWithUsersById(Long id);

    Page<Study> findAllWithUserAndBook(Pageable pageable);
    
}
