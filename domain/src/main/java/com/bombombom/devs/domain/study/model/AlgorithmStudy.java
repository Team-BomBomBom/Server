package com.bombombom.devs.domain.study.model;

import com.bombombom.devs.common.AlgoTag;
import com.bombombom.devs.domain.study.enums.StudyType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.graalvm.collections.Pair;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmStudy extends Study {

    private Float difficultyMath;

    private Float difficultyDp;

    private Float difficultyGreedy;

    private Float difficultyImpl;

    private Float difficultyGraph;

    private Float difficultyGeometry;

    private Float difficultyDs;

    private Float difficultyString;

    private Integer difficultyGap;

    private Integer problemCount;


    @Override
    public StudyType getStudyType() {
        return StudyType.ALGORITHM;
    }

    public Map<String, Pair<Integer, Integer>> getDifficultySpreadForEachTag() {
        return Map.of(
            AlgoTag.MATH.name(), getDifficultySpread(difficultyMath),
            AlgoTag.DP.name(), getDifficultySpread(difficultyDp),
            AlgoTag.GREEDY.name(), getDifficultySpread(difficultyGreedy),
            AlgoTag.IMPLEMENTATION.name(), getDifficultySpread(difficultyImpl),
            AlgoTag.GRAPHS.name(), getDifficultySpread(difficultyGraph),
            AlgoTag.GEOMETRY.name(), getDifficultySpread(difficultyGeometry),
            AlgoTag.DATA_STRUCTURES.name(), getDifficultySpread(difficultyDs),
            AlgoTag.STRING.name(), getDifficultySpread(difficultyString)
        );
    }

    private Pair<Integer, Integer> getDifficultySpread(Float difficulty) {
        Integer spreadLeft = Math.round(difficulty);
        Integer spreadRight = spreadLeft + difficultyGap;

        return Pair.create(spreadLeft, spreadRight);
    }


    public void assignProblemsToOngoingRound(LocalDate date, List<Long> problemIds) {
        Round round = getOngoingRound(date);
        round.assignProblems(problemIds);
    }

}
