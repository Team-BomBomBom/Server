package com.bombombom.devs.domain.study.model;

import com.bombombom.devs.mysql.BaseEntity;
import com.bombombom.devs.user.models.User;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "study")
@DiscriminatorColumn(name = "STUDY_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class StudyModel extends BaseEntity {

    protected Long id;

    protected String name;

    protected String introduce;

    protected Integer capacity;

    protected Integer headCount;

    protected Integer weeks;

    private User leader;

    protected LocalDate startDate;

    protected Integer reliabilityLimit;

    protected Integer penalty;

    protected StudyStatus state;

    protected List<UserStudy> userStudies;

    protected List<Round> rounds;

    public abstract StudyType getStudyType();

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
//        user.payMoney(penalty * weeks);
//        headCount++;
//        UserStudy userStudy = UserStudy.of(user, this, penalty * weeks);
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
