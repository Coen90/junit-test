package com.example.junitproject.dto;

import com.example.junitproject.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;

    public BookResponseDto toDto(Book bookPS) {
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}
