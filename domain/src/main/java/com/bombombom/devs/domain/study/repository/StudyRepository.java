package com.bombombom.devs.domain.study.repository;

import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.StudyModel;
import java.util.Optional;

public interface StudyRepository {

    Optional<StudyModel> findStudyWithUsersById(Long id);

    Page<StudyModel> findAllWithUserAndBook(Pageable pageable);

}
