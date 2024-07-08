package com.bombombom.devs.external.study.service;

import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterBookStudyCommand;
import java.util.ArrayList;

public class Mapper {

    public static AlgorithmStudy toModel(
        RegisterAlgorithmStudyCommand registerAlgorithmStudyCommand) {
        int difficultyGap = registerAlgorithmStudyCommand.difficultyEnd()
            - registerAlgorithmStudyCommand.difficultyBegin();
        float db = registerAlgorithmStudyCommand.difficultyBegin();

        return AlgorithmStudy.builder()
            .name(registerAlgorithmStudyCommand.name())
            .introduce(registerAlgorithmStudyCommand.introduce())
            .capacity(registerAlgorithmStudyCommand.capacity())
            .weeks(registerAlgorithmStudyCommand.weeks())
            .startDate(registerAlgorithmStudyCommand.startDate())
            .reliabilityLimit(registerAlgorithmStudyCommand.reliabilityLimit())
            .penalty(registerAlgorithmStudyCommand.penalty())
            .headCount(registerAlgorithmStudyCommand.headCount())
            .state(registerAlgorithmStudyCommand.state())
            .difficultyGraph(db)
            .difficultyString(db)
            .difficultyImpl(db)
            .difficultyMath(db)
            .difficultyDp(db)
            .difficultyGraph(db)
            .difficultyDs(db)
            .difficultyGeometry(db)
            .difficultyGreedy(db)
            .difficultyGap(difficultyGap)
            .problemCount(registerAlgorithmStudyCommand.problemCount())
            .members(new ArrayList<>())
            .rounds(new ArrayList<>())
            .build();
    }

    public static BookStudy toModel(
        RegisterBookStudyCommand registerBookStudyCommand) {
        return BookStudy.builder()
            .name(registerBookStudyCommand.name())
            .introduce(registerBookStudyCommand.introduce())
            .capacity(registerBookStudyCommand.capacity())
            .weeks(registerBookStudyCommand.weeks())
            .startDate(registerBookStudyCommand.startDate())
            .reliabilityLimit(registerBookStudyCommand.reliabilityLimit())
            .penalty(registerBookStudyCommand.penalty())
            .headCount(registerBookStudyCommand.headCount())
            .state(registerBookStudyCommand.state())
            .members(new ArrayList<>())
            .rounds(new ArrayList<>())
            .book(book)
            .build();
    }

}
