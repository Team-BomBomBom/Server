package com.bombombom.devs.external.video.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record GenerateSignedUrlRequest(
    @NotEmpty String videoId
) {

}
