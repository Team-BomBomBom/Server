package com.bombombom.devs.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseModel {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}
