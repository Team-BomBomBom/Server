package com.bombombom.devs.domain.study.model;

import com.bombombom.devs.domain.study.enums.StudyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookStudy extends Study {

    @Setter
    private Long bookId;

    @Override
    public StudyType getStudyType() {
        return StudyType.BOOK;
    }
}
