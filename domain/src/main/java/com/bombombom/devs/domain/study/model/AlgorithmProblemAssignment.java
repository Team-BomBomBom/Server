package com.bombombom.devs.domain.study.model;


import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmProblemAssignment {

    private Long id;

    private Round round;

    private Long problemId;

    private List<AlgorithmProblemAssignmentSolveHistory> solveHistories;

    public void createSolveHistories() {
        for (Long userId : round.getStudy().memberIds) {
            solveHistories.add(createSolveHistory(userId));
        }
    }

    private AlgorithmProblemAssignmentSolveHistory createSolveHistory(Long userId) {
        return AlgorithmProblemAssignmentSolveHistory.builder()
            .assignment(this)
            .userId(userId)
            .tryCount(0)
            .build();
    }
}
