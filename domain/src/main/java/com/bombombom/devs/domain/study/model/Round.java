package com.bombombom.devs.domain.study.model;

import java.time.LocalDate;
import java.util.ArrayList;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class Round {

    private Long id;

    private Study study;

    private Integer idx;

    private LocalDate startDate;

    private LocalDate endDate;

    public AlgorithmProblemAssignment assignProblem(Long problemId) {

        AlgorithmProblemAssignment assignment = AlgorithmProblemAssignment.builder()
            .round(this)
            .problemId(problemId)
            .solveHistories(new ArrayList<>())
            .build();
        assignment.createSolveHistories();
        
        return assignment;
    }
}
