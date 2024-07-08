package com.bombombom.devs.domain.study.vo;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record Round(
    Integer idx,
    LocalDate startDate,
    LocalDate endDate
) {

}
