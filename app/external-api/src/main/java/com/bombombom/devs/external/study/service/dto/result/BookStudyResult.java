package com.bombombom.devs.external.study.service.dto.result;

import com.bombombom.devs.book.service.dto.SearchBooksResult.BookResult;
import com.bombombom.devs.domain.study.enums.StudyStatus;
import com.bombombom.devs.domain.study.enums.StudyType;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record BookStudyResult(
    Long id,
    String name,
    String introduce,
    Integer capacity,
    Integer headCount,
    Integer weeks,
    LocalDate startDate,
    Integer reliabilityLimit,
    Integer penalty,
    StudyStatus state,
    UserProfileResult leader,
    StudyType studyType,
    BookResult bookResult
) implements StudyResult {

    public static BookStudyResult fromModel(BookStudy bookStudy, UserProfileResult leaderProfile,
        BookResult bookResult) {

        return BookStudyResult.builder()
            .id(bookStudy.getId())
            .name(bookStudy.getName())
            .introduce(bookStudy.getIntroduce())
            .capacity(bookStudy.getCapacity())
            .headCount(bookStudy.getHeadCount())
            .weeks(bookStudy.getWeeks())
            .startDate(bookStudy.getStartDate())
            .reliabilityLimit(bookStudy.getReliabilityLimit())
            .penalty(bookStudy.getPenalty())
            .state(bookStudy.getState())
            .leader(leaderProfile)
            .bookResult(bookResult)
            .studyType(bookStudy.getStudyType())
            .build();
    }
}