package com.example.junitproject.service;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.dto.BookResponseDto;
import com.example.junitproject.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<BookResponseDto> getBookList() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto)
                .collect(Collectors.toList());
    }

    // 3. 책한권보기
    public BookResponseDto getBook(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()) {
            return new BookResponseDto().toDto(bookOP.get());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 4. 책삭제


    // 5. 책수정
}
