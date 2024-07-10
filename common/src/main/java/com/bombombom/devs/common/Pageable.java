package com.bombombom.devs.common;

import lombok.Builder;

@Builder
public record Pageable(
    Integer size,
    Integer page
) {

}
