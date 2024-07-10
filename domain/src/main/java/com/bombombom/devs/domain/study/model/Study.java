package com.bombombom.devs.domain.study.model;

import com.bombombom.devs.domain.study.enums.StudyStatus;
import com.bombombom.devs.domain.study.enums.StudyType;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Study {

    public static final int MAX_CAPACITY = 20;

    public static final int MAX_WEEKS = 52;

    public static final int MAX_RELIABILITY_LIMIT = 100;

    public static final int MAX_PENALTY = 100_000;

    public static final int MAX_DIFFICULTY_LEVEL = 29;

    public static final int MAX_PROBLEM_COUNT = 20;

    protected Long id;

    protected String name;

    protected String introduce;

    protected Integer capacity;

    protected Integer headCount;

    protected Integer weeks;

    protected LocalDate startDate;

    protected Integer reliabilityLimit;

    protected Integer penalty;

    protected StudyStatus state;

    @Setter
    private Long leaderId;

    protected List<Long> memberIds;

    protected List<Round> rounds;


    public abstract StudyType getStudyType();

    public boolean canJoin(Long userId, Integer reliability) {

        if (memberIds.stream().anyMatch(memberId -> memberId.equals(userId))) {
            throw new IllegalStateException("Already Joined Study");
        }
        if (state.equals(StudyStatus.END)) {
            throw new IllegalStateException("The Study is End");
        }
        if (headCount >= capacity) {
            throw new IllegalStateException("The Study is full");
        }
        if (reliabilityLimit != null && reliability < reliabilityLimit) {
            throw new IllegalStateException("User reliability is low");
        }
        return true;
    }

    public Integer calculateDeposit() {
        return penalty * weeks;
    }

    public void createRounds() {
        for (int i = 0; i < weeks; i++) {
            createRound(i);
        }
    }

    private void createRound(int idx) {
        Round round = Round.builder()
            .idx(idx)
            .study(this)
            .startDate(startDate.plusWeeks(idx))
            .endDate(startDate.plusWeeks(idx + 1))
            .build();
        rounds.add(round);
    }

    public void join(Long userId) {
        memberIds.add(userId);
        headCount++;
    }

    public Round getOngoingRound(LocalDate date) {
        return rounds.stream().filter(round -> round.isWithinRange(date)).findFirst()
            .orElseThrow(() -> new IllegalStateException("Study is not ongoing"));
    }

}
