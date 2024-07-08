package com.bombombom.devs.domain.study.vo;

import java.time.LocalDate;

public record Round(
    Integer idx,
    LocalDate startDate,
    LocalDate endDate

) {

}
