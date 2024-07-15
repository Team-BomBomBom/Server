package com.bombombom.devs.external.study.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bombombom.devs.book.models.Book;
import com.bombombom.devs.book.repository.BookRepository;
import com.bombombom.devs.book.service.dto.SearchBooksResult;
import com.bombombom.devs.common.Page;
import com.bombombom.devs.common.Pageable;
import com.bombombom.devs.domain.study.enums.StudyStatus;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.domain.study.repository.StudyRepository;
import com.bombombom.devs.domain.user.enums.Role;
import com.bombombom.devs.domain.user.model.User;
import com.bombombom.devs.domain.user.repository.UserRepository;
import com.bombombom.devs.external.study.service.dto.command.JoinStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterAlgorithmStudyCommand;
import com.bombombom.devs.external.study.service.dto.command.RegisterBookStudyCommand;
import com.bombombom.devs.external.study.service.dto.result.AlgorithmStudyResult;
import com.bombombom.devs.external.study.service.dto.result.BookStudyResult;
import com.bombombom.devs.external.study.service.dto.result.StudyResult;
import com.bombombom.devs.external.user.service.dto.UserProfileResult;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    private StudyRepository studyRepository;
    @Mock
    private BookRepository bookRepository;


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudyService studyService;


    @Test
    @DisplayName("readStudy 메소드는 Page<StudyResult>를 반환한다")
    void read_study_returns_page_of_study_result() throws Exception {
        /*
        Given
         */
        List<Study> repositoryResponses = new ArrayList<>();

        Book book = Book.builder()
            .title("테스트용 책")
            .author("세계최강민석")
            .isbn(123456789L)
            .publisher("메가스터디")
            .tableOfContents("1. 2. 3. 4.")
            .build();
        User leader = User.builder()
            .id(5L)
            .username("leader")
            .role(Role.USER)
            .introduce("introduce")
            .image("image")
            .reliability(50)
            .money(10000)
            .build();
        Study study1 =
            AlgorithmStudy.builder()
                .reliabilityLimit(37)
                .introduce("안녕하세요")
                .name("스터디1")
                .startDate(LocalDate.of(2024, 06, 14))
                .penalty(5000)
                .weeks(5)
                .leaderId(leader.getId())
                .difficultyDp(12.4f)
                .difficultyDs(12f)
                .difficultyGraph(12.9f)
                .difficultyGap(5)
                .capacity(10)
                .difficultyGeometry(11f)
                .difficultyMath(11f)
                .difficultyString(13.5f)
                .problemCount(5)
                .build();

        Study study2 =
            BookStudy.builder()
                .reliabilityLimit(37)
                .capacity(10)
                .introduce("안녕하세요")
                .startDate(LocalDate.of(2024, 06, 14))
                .name("스터디1")
                .leaderId(leader.getId())
                .penalty(5000)
                .weeks(5)
                .bookId(book.getId())
                .build();

        repositoryResponses.add(study1);
        repositoryResponses.add(study2);

        Page<Study> studies = Page.<Study>builder()
            .contents(repositoryResponses)
            .build();
        when(studyRepository.findAll(any(Pageable.class))).thenReturn(studies);

        /*
        When
         */
        Page<StudyResult> studyResults = studyService.readStudy(
            Pageable.builder()
                .page(0)
                .size(10)
                .build()
        );

        /*
        Then
         */
        List<StudyResult> studyList = repositoryResponses.stream()
            .map(study -> StudyResult.fromModel(study, null, null)).toList();
        Page<StudyResult> expectedResponse = Page.<StudyResult>builder()
            .contents(studyList)
            .build();

        Assertions.assertThat(studyResults).isEqualTo(expectedResponse);

    }

    @Test
    @DisplayName("유저는 이미 가입한 스터디에 다시 가입할 수 없다.")
    void user_cannot_join_study_twice() {
        /*
         * Given
         */
        User testuser = User.builder()
            .id(1L)
            .username("testuser")
            .money(100000)
            .reliability(10)
            .build();
        Study study = AlgorithmStudy.builder()
            .capacity(10)
            .headCount(1)
            .weeks(10)
            .reliabilityLimit(10)
            .penalty(1000)
            .state(StudyStatus.READY)
            .build();
        JoinStudyCommand joinStudyCommand = JoinStudyCommand.builder().studyId(study.getId())
            .build();

        /*
         * When & Then
         */
        assertThatThrownBy(() -> studyService.joinStudy(
            testuser.getId(), joinStudyCommand))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Already Joined Study");
    }

    @Test
    @DisplayName("createAlgorithmStudy 메소드는 AlgorithmStudyResult를 반환한다")
    void create_algorithm_study_returns_algorithm_study_result() throws Exception {
        /*
        Given
         */
        User testuser = User.builder()
            .id(1L)
            .username("testuser")
            .money(100000)
            .reliability(40)
            .build();

        RegisterAlgorithmStudyCommand registerAlgorithmStudyCommand =
            RegisterAlgorithmStudyCommand.builder()
                .reliabilityLimit(37)
                .introduce("안녕하세요")
                .name("스터디1")
                .capacity(10)
                .startDate(LocalDate.of(2024, 06, 19))
                .penalty(5000)
                .weeks(5)
                .headCount(0)
                .state(StudyStatus.READY)
                .difficultyBegin(10)
                .difficultyEnd(15)
                .problemCount(5).build();

        AlgorithmStudy algorithmStudy = AlgorithmStudy.builder()
            .reliabilityLimit(37)
            .introduce("안녕하세요")
            .name("스터디1")
            .headCount(1)
            .capacity(10)
            .startDate(LocalDate.of(2024, 06, 19))
            .penalty(5000)
            .weeks(5)
            .state(StudyStatus.READY)
            .leaderId(testuser.getId())
            .difficultyDp(10f)
            .difficultyDs(10f)
            .difficultyImpl(10f)
            .difficultyGraph(10f)
            .difficultyGreedy(10f)
            .difficultyMath(10f)
            .difficultyString(10f)
            .difficultyGeometry(10f)
            .difficultyGap(5)
            .problemCount(5)
            .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testuser));


        /*
        When
         */
        AlgorithmStudyResult algorithmStudyResult = studyService.createAlgorithmStudy(
            testuser.getId(),
            registerAlgorithmStudyCommand);

        /*
        Then
         */
        StudyResult expectedResponse = StudyResult.fromModel(
            algorithmStudy, UserProfileResult.fromModel(testuser), null);

        Assertions.assertThat(algorithmStudyResult).isEqualTo(expectedResponse);
    }


    @Test
    @DisplayName("createBookStudy 메소드는 BookStudyResult를 반환한다")
    void create_book_study_returns_book_study_result() throws Exception {
        /*
        Given
         */
        User testuser = User.builder()
            .id(1L)
            .username("testuser")
            .money(100000)
            .reliability(40)
            .build();
        Book book = Book.builder()
            .title("테스트용 책")
            .author("세계최강민석")
            .isbn(123456789L)
            .publisher("메가스터디")
            .tableOfContents("1. 2. 3. 4.")
            .build();

        RegisterBookStudyCommand registerBookStudyCommand =
            RegisterBookStudyCommand.builder()
                .reliabilityLimit(37)
                .introduce("안녕하세요")
                .name("스터디1")
                .capacity(10)
                .startDate(LocalDate.of(2024, 06, 19))
                .penalty(5000)
                .weeks(5)

                .state(StudyStatus.READY)
                .headCount(0)
                .isbn(123456789L)
                .build();

        BookStudy bookStudy = BookStudy.builder()
            .reliabilityLimit(37)
            .introduce("안녕하세요")
            .name("스터디1")
            .headCount(1)
            .capacity(10)
            .startDate(LocalDate.of(2024, 06, 19))
            .penalty(5000)
            .leaderId(testuser.getId())
            .weeks(5)
            .bookId(book.getId())
            .state(StudyStatus.READY)
            .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testuser));
        when(bookRepository.findByIsbn(123456789L)).thenReturn(Optional.of(book));

        /*
        When
         */
        BookStudyResult bookStudyResult = studyService.createBookStudy(
            testuser.getId(),
            registerBookStudyCommand);

        /*
        Then
         */
        StudyResult expectedResponse = StudyResult.fromModel(
            bookStudy, UserProfileResult.fromModel(testuser), SearchBooksResult.fromEntity(book));

        Assertions.assertThat(bookStudyResult).isEqualTo(expectedResponse);
    }

}