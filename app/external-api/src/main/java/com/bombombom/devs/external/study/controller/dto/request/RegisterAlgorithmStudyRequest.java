package com.bombombom.devs.external.study.controller.dto.request;


import static com.bombombom.devs.study.model.Study.MAX_CAPACITY;
import static com.bombombom.devs.study.model.Study.MAX_DIFFICULTY_LEVEL;
import static com.bombombom.devs.study.model.Study.MAX_PENALTY;
import static com.bombombom.devs.study.model.Study.MAX_PROBLEM_COUNT;
import static com.bombombom.devs.study.model.Study.MAX_RELIABILITY_LIMIT;
import static com.bombombom.devs.study.model.Study.MAX_WEEKS;
import static com.bombombom.devs.study.model.Study.MIN_DIFFICULTY_LEVEL;

import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.study.enums.StudyStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record RegisterAlgorithmStudyRequest(
    @NotBlank @Size(max = 255, message = "스터디명은 255자를 넘을 수 없습니다.") String name,
    @NotBlank @Size(max = 500, message = "스터디소개는 500자를 넘을 수 없습니다.") String introduce,
    @NotNull @Range(min = 1, max = MAX_CAPACITY) Integer capacity,
    @NotNull @Range(min = 1, max = MAX_WEEKS) Integer weeks,
    @NotNull LocalDate startDate,
    @NotNull @Range(max = MAX_RELIABILITY_LIMIT) Integer reliabilityLimit,
    @NotNull @Range(max = MAX_PENALTY) Integer penalty,
    @NotNull @Range(min = MIN_DIFFICULTY_LEVEL, max = MAX_DIFFICULTY_LEVEL) Integer difficultyBegin,
    @NotNull @Range(min = MIN_DIFFICULTY_LEVEL, max = MAX_DIFFICULTY_LEVEL) Integer difficultyEnd,
    @NotNull @Range(min = 1, max = MAX_PROBLEM_COUNT) Integer problemCount
) {

    public RegisterAlgorithmStudyCommand toServiceDto() {

        return RegisterAlgorithmStudyCommand.builder()
            .name(name)
            .introduce(introduce)
            .capacity(capacity)
            .weeks(weeks)
            .startDate(startDate)
            .reliabilityLimit(reliabilityLimit)
            .penalty(penalty)
            .difficultyBegin(difficultyBegin)
            .difficultyEnd(difficultyEnd)
            .problemCount(problemCount)
            .state(StudyStatus.READY)
            .headCount(0)
            .build();

    }

    @AssertTrue
    private boolean isDifficultyBeginLteDifficultyEnd() {
        return difficultyBegin <= difficultyEnd;
    }

    @AssertTrue
    private boolean isStartDateAfterOrEqualToday() {
        LocalDate now = LocalDate.now();
        return startDate.isAfter(now) || startDate.isEqual(now);
    }
}
