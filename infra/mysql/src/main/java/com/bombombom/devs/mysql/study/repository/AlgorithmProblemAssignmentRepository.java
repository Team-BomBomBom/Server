package com.bombombom.devs.mysql.study.repository;

import com.bombombom.devs.mysql.study.entity.AlgorithmProblemAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmProblemAssignmentRepository
    extends JpaRepository<AlgorithmProblemAssignment, Long> {

}
