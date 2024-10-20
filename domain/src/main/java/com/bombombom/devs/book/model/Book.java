package com.bombombom.devs.book.model;

import com.bombombom.devs.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false, unique = true)
    private Long isbn;

    @Column(name = "table_of_contents")
    private String tableOfContents;

    @Column(name = "image_url")
    private String imageUrl;

    public static Book fromBookDocument(BookDocument bookDocument) {
        return Book.builder()
            .title(bookDocument.getTitle())
            .author(bookDocument.getAuthor())
            .publisher(bookDocument.getPublisher())
            .isbn(bookDocument.getIsbn())
            .tableOfContents(bookDocument.getTableOfContents())
            .imageUrl(bookDocument.getImageUrl())
            .build();
    }
}
