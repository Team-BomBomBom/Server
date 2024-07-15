package com.bombombom.devs.mysql.study.entity;

import com.bombombom.devs.domain.study.enums.StudyType;
import com.bombombom.devs.domain.study.model.BookStudy;
import com.bombombom.devs.domain.study.model.Study;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@SuperBuilder
@Table(name = "book_study")
@DiscriminatorValue(StudyType.Values.BOOK)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookStudyEntity extends StudyEntity {

    @Column(
        name = "book_id",
        nullable = false)
    private Long bookId;

    @Override
    public StudyType getStudyType() {
        return StudyType.BOOK;
    }

    @Override
    public Study toModel() {
        return BookStudy.builder()
            .id(getBookId())
            .name(getName())
            .introduce(getIntroduce())
            .capacity(getCapacity())
            .headCount(getHeadCount())
            .weeks(getWeeks())
            .startDate(getStartDate())
            .reliabilityLimit(getReliabilityLimit())
            .penalty(getPenalty())
            .state(getState())
            .leaderId(getLeaderId())
            .bookId(getBookId())
            .build();

    }

    public static BookStudyEntity fromModel(BookStudy bookStudy) {
        return BookStudyEntity.builder()
            .id(bookStudy.getId())
            .name(bookStudy.getName())
            .introduce(bookStudy.getIntroduce())
            .capacity(bookStudy.getCapacity())
            .headCount(bookStudy.getHeadCount())
            .weeks(bookStudy.getWeeks())
            .startDate(bookStudy.getStartDate())
            .reliabilityLimit(bookStudy.getReliabilityLimit())
            .penalty(bookStudy.getPenalty())
            .state(bookStudy.getState())
            .leaderId(bookStudy.getLeaderId())
            .bookId(bookStudy.getBookId())

            .build();
    }
}
