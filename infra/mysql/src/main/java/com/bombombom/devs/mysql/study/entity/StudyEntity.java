package com.bombombom.devs.mysql.study.entity;

import com.bombombom.devs.domain.study.enums.StudyStatus;
import com.bombombom.devs.domain.study.enums.StudyType;
import com.bombombom.devs.domain.study.model.AlgorithmStudy;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import com.bombombom.devs.mysql.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
public abstract class StudyEntity extends BaseEntity {

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

    @Column(nullable = false, name = "leader_id")
    private Long leaderId;

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


    public abstract Study toModel();

    public static StudyEntity fromModel(Study study) {
        if (study instanceof BookStudy bookStudy) {
            return BookStudyEntity.fromModel(bookStudy);
        } else if (study instanceof AlgorithmStudy algorithmStudy) {
            return AlgorithmStudyEntity.fromModel(algorithmStudy);
        } else {
            throw new IllegalStateException("Study Type is incorrect");
        }
    }
}
