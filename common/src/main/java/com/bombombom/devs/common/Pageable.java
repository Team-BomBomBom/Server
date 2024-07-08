package com.bombombom.devs.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public record Pageable(
    Integer size,
    Integer page
) {

}
