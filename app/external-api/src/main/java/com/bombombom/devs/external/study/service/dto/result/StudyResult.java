package com.bombombom.devs.external.study.service.dto.result;

import com.bombombom.devs.domain.study.enums.StudyStatus;
import com.bombombom.devs.domain.study.enums.StudyType;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import java.time.LocalDate;

public interface StudyResult {

    Long id();

    String name();

    String introduce();

    Integer capacity();

    Integer headCount();

    Integer weeks();

    LocalDate startDate();

    Integer reliabilityLimit();

    Integer penalty();

    StudyStatus state();

    StudyType studyType();

    UserProfileResult leader();

    static StudyResult fromEntity(Study study) {
        if (study instanceof AlgorithmStudy algorithmStudy) {
            return AlgorithmStudyResult.fromEntity(algorithmStudy);
        } else if (study instanceof BookStudy bookStudy) {
            return BookStudyResult.fromModel(bookStudy);
        } else {
            return null;
        }
    }
}
