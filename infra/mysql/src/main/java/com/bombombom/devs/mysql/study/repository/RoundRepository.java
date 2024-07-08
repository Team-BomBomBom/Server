package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.mysql.study.entity.Round;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select r from Round r "
        + "join fetch r.study s "
        + "join fetch s.userStudies us "
        + "join fetch us.user "
        + "where r.startDate = :startDate")
    List<Round> findRoundsWithStudyByStartDate(LocalDate startDate);
}

