package com.bombombom.devs.study.service.dto.command;

import com.bombombom.devs.study.models.StudyStatus;
import com.bombombom.devs.study.service.StudyService;
import jakarta.validation.constraints.NotNull;
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
