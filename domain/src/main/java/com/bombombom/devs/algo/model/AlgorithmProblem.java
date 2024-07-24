package com.bombombom.devs.algo.model;

import com.bombombom.devs.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "algorithm_problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmProblem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ref_id", unique = true)
    private Integer refId;

    private AlgoTag tag;

    private String title;

    private String link;

    private Integer difficulty;
}
