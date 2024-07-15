package com.bombombom.devs.mysql.study.entity;

import com.bombombom.devs.domain.study.enums.StudyType;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.Study;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "algorithm_study")
@DiscriminatorValue(StudyType.Values.ALGORITHM)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmStudyEntity extends StudyEntity {

    @Column(name = "difficulty_math")
    private Float difficultyMath;

    @Column(name = "difficulty_dp")
    private Float difficultyDp;

    @Column(name = "difficulty_greedy")
    private Float difficultyGreedy;

    @Column(name = "difficulty_impl")
    private Float difficultyImpl;

    @Column(name = "difficulty_graph")
    private Float difficultyGraph;

    @Column(name = "difficulty_geometry")
    private Float difficultyGeometry;

    @Column(name = "difficulty_ds")
    private Float difficultyDs;

    @Column(name = "difficulty_string")
    private Float difficultyString;

    @Column(name = "difficulty_gap")
    private Integer difficultyGap;

    @Column(name = "problem_count")
    private Integer problemCount;

    @Override
    public StudyType getStudyType() {
        return StudyType.ALGORITHM;
    }

    @Override
    public Study toModel() {
        return AlgorithmStudy.builder()
            .id(getId())
            .name(getName())
            .introduce(getIntroduce())
            .capacity(getCapacity())
            .headCount(getHeadCount())
            .weeks(getWeeks())
            .startDate(getStartDate())
            .reliabilityLimit(getReliabilityLimit())
            .penalty(getPenalty())
            .state(getState())
            .leaderId(getLeaderId())
            .difficultyDs(getDifficultyDs())
            .difficultyGraph(getDifficultyGraph())
            .difficultyString(getDifficultyString())
            .difficultyGeometry(getDifficultyGeometry())
            .difficultyMath(getDifficultyMath())
            .difficultyImpl(getDifficultyImpl())
            .difficultyGap(getDifficultyGap())
            .difficultyGreedy(getDifficultyGreedy())
            .problemCount(getProblemCount())
            .difficultyDp(getDifficultyDp())
            .build();
    }


    public static AlgorithmStudyEntity fromModel(AlgorithmStudy algorithmStudy) {
        return AlgorithmStudyEntity.builder()
            .id(algorithmStudy.getId())
            .name(algorithmStudy.getName())
            .introduce(algorithmStudy.getIntroduce())
            .capacity(algorithmStudy.getCapacity())
            .headCount(algorithmStudy.getHeadCount())
            .weeks(algorithmStudy.getWeeks())
            .startDate(algorithmStudy.getStartDate())
            .reliabilityLimit(algorithmStudy.getReliabilityLimit())
            .penalty(algorithmStudy.getPenalty())
            .leaderId(algorithmStudy.getLeaderId())
            .state(algorithmStudy.getState())
            .difficultyDs(algorithmStudy.getDifficultyDs())
            .difficultyGraph(algorithmStudy.getDifficultyGraph())
            .difficultyString(algorithmStudy.getDifficultyString())
            .difficultyGeometry(algorithmStudy.getDifficultyGeometry())
            .difficultyMath(algorithmStudy.getDifficultyMath())
            .difficultyImpl(algorithmStudy.getDifficultyImpl())
            .difficultyGap(algorithmStudy.getDifficultyGap())
            .difficultyGreedy(algorithmStudy.getDifficultyGreedy())
            .problemCount(algorithmStudy.getProblemCount())
            .difficultyDp(algorithmStudy.getDifficultyDp())
            .build();
    }

}
