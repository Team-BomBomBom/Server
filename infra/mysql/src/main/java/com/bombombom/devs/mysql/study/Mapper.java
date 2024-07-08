package com.bombombom.devs.mysql.study;

import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.mysql.study.entity.AlgorithmStudyEntity;
import com.bombombom.devs.mysql.study.entity.BookStudyEntity;
import com.bombombom.devs.mysql.study.entity.StudyEntity;

public class Mapper {

    public static Study toModel(StudyEntity studyEntity) {

        switch (studyEntity.getStudyType()) {
            case BOOK -> {
                return BookStudy.builder().build();
            }
            case ALGORITHM -> {
                return AlgorithmStudy.builder().build();
            }
            default -> throw new IllegalStateException("Study Type is incorrect");
        }
    }

    public static StudyEntity toEntity(Study study) {
        switch (study.getStudyType()) {
            case BOOK -> {
                return BookStudyEntity.builder().build();
            }
            case ALGORITHM -> {
                return AlgorithmStudyEntity.builder().build();
            }
            default -> throw new IllegalStateException("Study Type is incorrect");

        }
    }
}
