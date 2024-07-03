package com.bombombom.devs.user.controller.dto;

import com.bombombom.devs.user.models.Role;
import com.bombombom.devs.user.service.dto.UserProfileResult;
import lombok.Builder;

@Builder
public record UserProfileResponse(
    Long id,
    String username,
    String image,
    String introduce,
    Integer reliability,
    Integer money,
    Role role
) {

    public static UserProfileResponse fromResult(UserProfileResult result) {
        return UserProfileResponse.builder()
            .id(result.id())
            .username(result.username())
            .role(result.role())
            .image(result.image())
            .introduce(result.introduce())
            .reliability(result.reliability())
            .money(result.money())
            .build();
    }
}
