package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.domain.study.model.Page;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.domain.study.vo.Pageable;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class StudyEntityRepository implements StudyRepository {


    @Override
    public Optional<Study> findStudyWithUsersById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Study> findAllWithUserAndBook(Pageable pageable) {
        return null;
    }
}
