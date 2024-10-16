package com.bombombom.devs.external.study.service.dto.command;

import com.bombombom.devs.study.enums.StudyStatus;
import java.time.LocalDate;

public interface RegisterStudyCommand {

    String name();

    String introduce();

    Integer capacity();

    Integer weeks();

    LocalDate startDate();

    Integer reliabilityLimit();

    Integer penalty();

    StudyStatus state();

    Integer headCount();


}
