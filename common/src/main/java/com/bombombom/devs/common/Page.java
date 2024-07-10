package com.bombombom.devs.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Page<T> {

    private List<T> contents;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNumber;

    public <R> Page<R> map(Function<? super T, ? extends R> mapper) {

        return Page.<R>builder()
            .contents(contents.stream().map(mapper).collect(Collectors.toList()))
            .totalElements(totalElements)
            .totalPages(totalPages)
            .pageNumber(pageNumber)
            .build();
    }

}
