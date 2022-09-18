package com.example.junitproject.service;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.dto.BookResponseDto;
import com.example.junitproject.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResponseDto addBook(BookSaveRequestDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookResponseDto().toDto(bookPS);
    }

    // 2. 책 목록보기


    // 3. 책한건보기


    // 4. 책삭제


    // 5. 책수정
}
