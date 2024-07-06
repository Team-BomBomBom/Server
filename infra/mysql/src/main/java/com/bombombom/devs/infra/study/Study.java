package com.bombombom.devs.infra.study;

import com.bombombom.devs.infra.BaseEntity;
import com.bombombom.devs.user.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public abstract class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false, length = 500)
    protected String introduce;

    @Column
    protected Integer capacity;

    @Column(nullable = false, name = "head_count")
    protected Integer headCount;

    @Column
    protected Integer weeks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id",
        nullable = false,
        foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User leader;

    @Column(name = "start_date")
    protected LocalDate startDate;

    @Column(name = "reliability_limit")
    protected Integer reliabilityLimit;

    @Column
    protected Integer penalty;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected StudyStatus state;

    @OneToMany(mappedBy = "study", cascade = CascadeType.PERSIST)
    protected List<UserStudy> userStudies;

    @OneToMany(mappedBy = "study", cascade = CascadeType.PERSIST)
    protected List<Round> rounds;

    public abstract StudyType getStudyType();

    public UserStudy join(User user) {
        if (state.equals(StudyStatus.END)) {
            throw new IllegalStateException("The Study is End");
        }
        if (headCount >= capacity) {
            throw new IllegalStateException("The Study is full");
        }
        if (reliabilityLimit != null && user.getReliability() < reliabilityLimit) {
            throw new IllegalStateException("User reliability is low");
        }
        user.payMoney(penalty * weeks);
        headCount++;
        UserStudy userStudy = UserStudy.of(user, this, penalty * weeks);
        userStudies.add(userStudy);

        return userStudy;
    }

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
