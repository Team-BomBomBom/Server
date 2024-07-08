package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.mysql.study.Mapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudyEntityRepository implements StudyRepository {


    StudyJpaRepository studyJpaRepository;

    @Override
    public Optional<Study> findStudyWithUsersById(Long id) {
        return Optional.empty();
    }

    @Override
    public Study save(Study study) {
        return Mapper.toModel(studyJpaRepository.save(Mapper.toEntity(study)));
    }

    @Override
    public Page<Study> findAllWithUserAndBook(Pageable pageable) {
        return null;
    }
}
