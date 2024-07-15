package com.bombombom.devs.external.study.service;

import com.bombombom.devs.book.service.BookService;
import com.bombombom.devs.book.service.dto.SearchBooksResult.BookResult;
import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.external.study.service.dto.command.JoinStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterBookStudyCommand;
import com.bombombom.devs.external.study.service.dto.result.AlgorithmStudyResult;
import com.bombombom.devs.external.study.service.dto.result.BookStudyResult;
import com.bombombom.devs.external.study.service.dto.result.StudyResult;
import com.bombombom.devs.external.user.service.UserService;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import com.bombombom.devs.global.util.Clock;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final Clock clock;
    private final StudyRepository studyRepository;
    private final UserService userService;
    private final BookService bookService;

    @Transactional
    public AlgorithmStudyResult createAlgorithmStudy(
        Long userId,
        RegisterAlgorithmStudyCommand registerAlgorithmStudyCommand) {

        final UserProfileResult user = userService.findById(userId);

        AlgorithmStudy algorithmStudy = Mapper.toModel(registerAlgorithmStudyCommand);

        algorithmStudy.setLeaderId(userId);
        algorithmStudy.createRounds();
        algorithmStudy.join(userId);

        studyRepository.save(algorithmStudy);

        return AlgorithmStudyResult.fromModel(algorithmStudy, user);
    }

    @Transactional
    public BookStudyResult createBookStudy(
        Long userId, RegisterBookStudyCommand registerBookStudyCommand) {

        //user 서비스 API 콜
        final UserProfileResult user = userService.findById(userId);

        //Book 서비스 API 콜
        BookResult bookResult = bookService.findBookByIsbn(registerBookStudyCommand.isbn());

        BookStudy bookStudy = Mapper.toModel(registerBookStudyCommand);

        bookStudy.setLeaderId(userId);
        bookStudy.setBookId(bookResult.id());
        bookStudy.join(userId);
        bookStudy.createRounds();

        studyRepository.save(bookStudy);
        return BookStudyResult.fromModel(bookStudy, user, bookResult);
    }

    @Transactional(readOnly = true)
    public Page<StudyResult> readStudy(Pageable pageable) {
        Page<Study> studyPage = studyRepository.findAll(pageable);

        Stream<Study> studies = studyPage.getContents().stream();

        List<Long> leaderIds = studies.map(Study::getLeaderId).toList();
        List<UserProfileResult> leaderProfiles = userService.findAllById(leaderIds);

        List<Long> bookIds = studies.map(study -> {
                if (study instanceof BookStudy bookStudy) {
                    return bookStudy.getBookId();
                }
                return null;
            }
        ).toList();
        List<BookResult> bookResults = bookService.findAllBookByIsbn(bookIds);

        AtomicInteger index = new AtomicInteger(0);
        ;
        return studyPage.map(
            (study) -> StudyResult.fromModel(study, leaderProfiles.get(index.get()),
                bookResults.get(index.getAndIncrement())));
    }

    @Transactional
    public void joinStudy(Long userId, JoinStudyCommand joinStudyCommand) {
        //TODO 로킹 필요
        UserProfileResult userProfile = userService.findById(userId);

        Study study = studyRepository.findStudyWithUsersById(joinStudyCommand.studyId())
            .orElseThrow(
                () -> new IllegalStateException("Study Not Found"));

        if (!study.canJoin(userProfile.id(), userProfile.reliability())) {
            return;
        }

        studyRepository.save(study);
    }


    @Transactional
    public List<Study> findStudyHavingRoundToStart() {
        return studyRepository.findStudyHavingRoundToStartWithUsers(clock.today());
    }

}
