package com.example.junitproject.web;

import com.example.junitproject.dto.response.BookResponseDto;
import com.example.junitproject.dto.request.BookSaveRequestDto;
import com.example.junitproject.dto.response.CommonResponseDto;
import com.example.junitproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookApiController { // 컴포지션 = has 관계
    
    private final BookService bookService;
    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveRequestDto bookSaveRequestDto) {
        BookResponseDto bookResponseDto = bookService.addBook(bookSaveRequestDto);
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.CREATED); // 201 == insert
    }

    // 2. 책 목록 보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책 한건 보기
    public ResponseEntity<?> getBook() {
        return null;
    }

    // 4. 책 삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책 수정하기
    public ResponseEntity<?> editBook() {
        return null;
    }

}
