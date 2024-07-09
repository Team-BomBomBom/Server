package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.mysql.study.Mapper;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class StudyEntityRepository implements StudyRepository {

    StudyJpaRepository studyJpaRepository;

    @Override
    public Optional<Study> findStudyWithUsersById(Long id) {

        return studyJpaRepository.findStudyWithUsersById(id).map(Mapper::toModel);
    }

    @Override
    public Study save(Study study) {
        return Mapper.toModel(studyJpaRepository.save(Mapper.toEntity(study)));
    }

    @Override
    public Page<Study> findAllWithUserAndBook(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.page(), pageable.size());

        org.springframework.data.domain.Page<Study> page
            = studyJpaRepository.findAllWithUserAndBook(pageRequest).map(Mapper::toModel);

        return Page.<Study>builder()
            .pageNumber(page.getNumber())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .contents(page.getContent())
            .build();

    }
}
