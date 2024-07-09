package com.bombombom.devs.common;

import java.util.List;
import lombok.Builder;

@Builder
public record Page<T>(

    List<T> contents,
    Long totalElements,
    Integer totalPages,
    Integer pageNumber
) {

}
