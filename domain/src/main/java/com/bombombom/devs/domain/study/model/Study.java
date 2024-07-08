package com.bombombom.devs.domain.study.model;

import com.bombombom.devs.domain.BaseModel;
import com.bombombom.devs.domain.user.model.User;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Study extends BaseModel {

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

    private User leader;


    protected List<User> users;

    protected List<Round> rounds;


    public boolean canJoin(User user) {
        if (state.equals(StudyStatus.END)) {
            throw new IllegalStateException("The Study is End");
        }
        if (headCount >= capacity) {
            throw new IllegalStateException("The Study is full");
        }
        if (reliabilityLimit != null && user.getReliability() < reliabilityLimit) {
            throw new IllegalStateException("User reliability is low");
        }
        return true;
    }

    public Integer calculateDeposit() {
        return penalty * weeks;
    }

    public void addHeadCount(Integer headCount) {
        this.headCount += headCount;
    }

//    public UserStudy join(User user) {
//        if (state.equals(StudyStatus.END)) {
//            throw new IllegalStateException("The Study is End");
//        }
//        if (headCount >= capacity) {
//            throw new IllegalStateException("The Study is full");
//        }
//        if (reliabilityLimit != null && user.getReliability() < reliabilityLimit) {
//            throw new IllegalStateException("User reliability is low");
//        }
    // 여기까지 canJoinStudy
//        user.payMoney(penalty * weeks);
    // penalty*weeks는 deposit = calculateDeposit(Study);
    // user.takeMoney(deposit)는 모델 상태를 변경하는 거니 존재하는게 맞음.
//        headCount++;
    // study.addHeadCount(1);
//        UserStudy userStudy = UserStudy.of(user, this, penalty * weeks);
    // UserStudyModel = userStudyModel.of(user, study, deposit);
//        userStudies.add(userStudy);
//
//        return userStudy;
//    }

    public List<String> getBaekjoonIds() {
        return userStudies.stream()
            .map(userStudy -> userStudy.getUser().getBaekjoon())
            .toList();
    }

    public void createRounds() {
        for (int i = 0; i < weeks; i++) {
            createRound(i);
        }
    }

    private void createRound(int idx) {
        Round round = Round.builder()
            .study(this)
            .idx(idx)
            .startDate(startDate.plusWeeks(idx))
            .endDate(startDate.plusWeeks(idx + 1))
            .build();
        rounds.add(round);
    }
}
