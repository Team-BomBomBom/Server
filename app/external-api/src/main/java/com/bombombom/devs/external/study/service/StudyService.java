package com.bombombom.devs.external.study.service;

import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import com.bombombom.devs.external.study.service.dto.command.JoinStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterBookStudyCommand;
import com.bombombom.devs.external.study.service.dto.result.AlgorithmStudyResult;
import com.bombombom.devs.external.study.service.dto.result.BookStudyResult;
import com.bombombom.devs.external.study.service.dto.result.StudyResult;
import com.bombombom.devs.global.util.Clock;
import com.bombombom.devs.study.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

        AlgorithmStudy algorithmStudy = Mapper.toModel(registerAlgorithmStudyCommand);

        algorithmStudy.setLeaderId(userId);
        algorithmStudy.createRounds();
        algorithmStudy.join(userId);

        studyRepository.save(algorithmStudy);

        return AlgorithmStudyResult.fromEntity(algorithmStudy);
    }

    @Transactional
    public BookStudyResult createBookStudy(
        Long userId, RegisterBookStudyCommand registerBookStudyCommand) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User Not Found"));

        //Book 서비스에 API 콜 발생
        Book book = bookRepository.findByIsbn(registerBookStudyCommand.isbn())
            .orElseThrow(() -> new NotFoundException("Book Not Found"));

        BookStudy bookStudy = Mapper.toModel(registerBookStudyCommand);

        bookStudy.setLeaderId(userId);
        bookStudy.setBookId(book.getId());
        bookStudy.join(userId);
        bookStudy.createRounds();

        studyRepository.save(bookStudy);

        return BookStudyResult.fromModel(bookStudy);
    }

    @Transactional(readOnly = true)
    public Page<StudyResult> readStudy(Pageable pageable) {
        Page<Study> studyPage = studyRepository.findAll(pageable);

        return studyPage.map(StudyResult::fromEntity);
    }

    @Transactional
    public void joinStudy(Long userId, JoinStudyCommand joinStudyCommand) {
        //TODO 로킹 필요
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User Not Found"));

        Study study = studyRepository.findStudyWithUsersById(joinStudyCommand.studyId())
            .orElseThrow(
                () -> new IllegalStateException("Study Not Found"));

        if (!study.canJoin(user.getId(), user.getReliability())) {
            return;
        }

        studyRepository.save(study);
    }


    @Transactional
    public List<Study> findHavingRoundToStart() {
        return studyRepository.findStudyHavingRoundToStartWithUsers(clock.today());
    }

}
