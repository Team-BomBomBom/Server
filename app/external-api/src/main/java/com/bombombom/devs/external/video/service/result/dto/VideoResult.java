package com.bombombom.devs.external.video.service.result.dto;

import com.bombombom.devs.external.study.service.dto.result.AssignmentResult;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import com.bombombom.devs.study.model.Video;
import lombok.Builder;

@Builder
public record VideoResult(

    AssignmentResult assignmentResult,
    UserProfileResult profile
) {

    public static VideoResult fromEntity(Video video) {
        return VideoResult.builder()
            .assignmentResult(AssignmentResult.fromEntity(video.getAssignment()))
            .profile(UserProfileResult.fromEntity(video.getUploader()))
            .build();
    }
}
