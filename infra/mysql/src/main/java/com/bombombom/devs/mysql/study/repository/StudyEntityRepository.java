package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.mysql.study.Mapper;
import com.bombombom.devs.mysql.study.entity.Round;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class StudyEntityRepository implements StudyRepository {

    StudyJpaRepository studyJpaRepository;
    RoundRepository roundRepository;


    @Override
    public Optional<Study> findStudyWithUsersById(Long id) {
        return studyJpaRepository.findWithUsersById(id).map(Mapper::toModel);
    }


    @Override
    public Study save(Study study) {
        return Mapper.toModel(studyJpaRepository.save(Mapper.toEntity(study)));
    }

    @Override
    public Page<Study> findAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.page(), pageable.size());

        org.springframework.data.domain.Page<Study> page
            = studyJpaRepository.findAll(pageRequest).map(Mapper::toModel);
        return Page.<Study>builder()
            .pageNumber(page.getNumber())
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .contents(page.getContent())
            .build();

    }

    @Override
    public List<Study> findStudyHavingRoundToStartWithUsers(LocalDate localDate) {
        List<Round> rounds = roundRepository.findRoundsWithStudyByStartDate(localDate);

        return rounds.stream().map(Round::getStudy).map(Mapper::toModel).toList();


    }


}
