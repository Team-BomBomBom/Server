package com.bombombom.devs.external.study.service;

import com.bombombom.devs.algo.models.AlgorithmProblem;
import com.bombombom.devs.book.models.Book;
import com.bombombom.devs.client.solvedac.dto.ProblemListResponse;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.external.study.service.dto.command.JoinStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterBookStudyCommand;
import com.bombombom.devs.external.study.service.dto.result.AlgorithmStudyResult;
import com.bombombom.devs.external.study.service.dto.result.BookStudyResult;
import com.bombombom.devs.external.study.service.dto.result.StudyResult;
import com.bombombom.devs.global.util.Clock;
import com.bombombom.devs.study.exception.NotFoundException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final Clock clock;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
//    private final BookRepository bookRepository;
//    private final UserStudyRepository userStudyRepository;
//    private final SolvedacClient solvedacClient;
//    private final RoundRepository roundRepository;
//    private final AlgorithmProblemRepository algoProblemRepository;
//    private final AlgorithmProblemAssignmentRepository algorithmProblemAssignmentRepository;
//    private final AlgorithmProblemConverter algorithmProblemConverter;

    @Transactional
    public AlgorithmStudyResult createAlgorithmStudy(
        Long userId,
        RegisterAlgorithmStudyCommand registerAlgorithmStudyCommand) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User Not Found"));

        AlgorithmStudy algorithmStudy = Mapper.toDomainModel(registerAlgorithmStudyCommand);

        algorithmStudy.setLeader(user);
        algorithmStudy.createRounds();
        algorithmStudy.join(user);
        studyRepository.save(algorithmStudy);

        return AlgorithmStudyResult.fromEntity(algorithmStudy);
    }

    @Transactional
    public BookStudyResult createBookStudy(
        Long userId, RegisterBookStudyCommand registerBookStudyCommand) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User Not Found"));

        Book book = bookRepository.findByIsbn(registerBookStudyCommand.isbn())
            .orElseThrow(() -> new NotFoundException("Book Not Found"));

        BookStudy bookStudy = Mapper.toDomainModel(registerBookStudyCommand);

        bookStudy.createRounds();
        bookStudy.join(user);
        studyRepository.save(bookStudy);

        return BookStudyResult.fromModel(bookStudy);
    }

    @Transactional(readOnly = true)
    public Page<StudyResult> readStudy(Pageable pageable) {
        Page<Study> studyPage = studyRepository.findAllWithUserAndBook(pageable);

        return studyPage.map(StudyResult::fromEntity);

    }

    @Transactional
    public void joinStudy(Long userId, JoinStudyCommand joinStudyCommand) {
        if (userStudyRepository.existsByUserIdAndStudyId(userId, joinStudyCommand.studyId())) {
            throw new IllegalStateException("Already Joined Study");
        }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User Not Found"));
        Study study = studyRepository.findById(joinStudyCommand.studyId())
            .orElseThrow(
                () -> new IllegalStateException("Study Not Found"));
        UserStudy userStudy = study.join(user);
        userStudyRepository.save(userStudy);
    }

    @Transactional
    public List<AlgorithmProblem> getUnSolvedProblemListAndSave(
        AlgorithmStudy study,
        Map<String, Integer> problemCountForEachTag
    ) {
        ProblemListResponse problemListResponse = solvedacClient.getUnSolvedProblems(
            study.getBaekjoonIds(), problemCountForEachTag, study.getDifficultySpreadForEachTag());
        List<AlgorithmProblem> problems = algorithmProblemConverter.convert(problemListResponse);
        return algoProblemRepository.saveAll(problems);
    }

    @Transactional
    public void assignProblemToRound(
        Round round, List<AlgorithmProblem> unSolvedProblems) {
        List<AlgorithmProblemAssignment> assignments = round.assignProblems(unSolvedProblems);
        algorithmProblemAssignmentRepository.saveAll(assignments);
    }

    @Transactional
    public List<Round> findRoundsHaveToStart() {
        return roundRepository.findRoundsWithStudyByStartDate(clock.today());
    }

}
