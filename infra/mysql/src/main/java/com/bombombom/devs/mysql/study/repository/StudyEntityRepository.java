package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.StudyModel;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudyEntityRepository implements StudyRepository {


    @Override
    public Optional<StudyModel> findStudyWithUsersById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<StudyModel> findAllWithUserAndBook(Pageable pageable) {
        return null;
    }
}
