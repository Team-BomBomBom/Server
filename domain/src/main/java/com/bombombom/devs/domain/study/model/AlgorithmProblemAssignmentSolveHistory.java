package com.bombombom.devs.domain.study.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmProblemAssignmentSolveHistory {

    private Long id;

    private Long userId;

    private AlgorithmProblemAssignment assignment;

    private LocalDateTime solvedAt;

    private Integer tryCount;
}
