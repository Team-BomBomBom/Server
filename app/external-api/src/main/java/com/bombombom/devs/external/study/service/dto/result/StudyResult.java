package com.bombombom.devs.external.study.service.dto.result;

import com.bombombom.devs.book.service.dto.SearchBooksResult.BookResult;
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

    static StudyResult fromModel(Study study, UserProfileResult leaderProfile,
        BookResult bookResult) {
        if (study instanceof AlgorithmStudy algorithmStudy) {
            return AlgorithmStudyResult.fromModel(algorithmStudy, leaderProfile);
        } else if (study instanceof BookStudy bookStudy) {
            return BookStudyResult.fromModel(bookStudy, leaderProfile, bookResult);
        } else {
            return null;
        }
    }
}
