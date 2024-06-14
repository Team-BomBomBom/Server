package com.bombombom.devs.study.service.dto.command;

import com.bombombom.devs.study.models.BookStudy;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RegisterBookStudyCommand(
    @NotNull String name,
    @NotNull String introduce,
    Integer capacity,
    Integer weeks,
    @NotNull LocalDate startDate,
    Integer reliabilityLimit,
    Integer penalty,
    Long bookId
) {

    public BookStudy toEntity() {

        return BookStudy.builder()
            .name(name)
            .introduce(introduce)
            .capacity(capacity)
            .weeks(weeks)
            .startDate(startDate)
            .reliabilityLimit(reliabilityLimit)
            .penalty(penalty)
            .bookId(bookId)
            .build();

    }


}
