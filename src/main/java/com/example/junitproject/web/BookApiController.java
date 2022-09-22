package com.example.junitproject.web;

import com.example.junitproject.web.dto.request.BookSaveRequestDto;
import com.example.junitproject.web.dto.response.BookListResponseDto;
import com.example.junitproject.web.dto.response.BookResponseDto;
import com.example.junitproject.web.dto.response.CommonResponseDto;
import com.example.junitproject.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController { // 컴포지션 = has 관계
    
    private final BookService bookService;
    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveRequestDto bookSaveRequestDto, BindingResult bindingResult) {

        // AOP 처리하는게 좋음!
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            log.debug("==========================================================");
            System.out.println("errorMap = " + errorMap.toString());
            log.debug("==========================================================");

            throw new RuntimeException(errorMap.toString());
        }
        BookResponseDto bookResponseDto = bookService.addBook(bookSaveRequestDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); // 201 == insert
    }

    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBookV2(@RequestBody @Valid BookSaveRequestDto bookSaveRequestDto, BindingResult bindingResult) {

        BookResponseDto bookResponseDto = bookService.addBook(bookSaveRequestDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); // 201 == insert
    }

    // 2. 책 목록 보기
    @GetMapping("/api/v1/books")
    public ResponseEntity<?> getBookList() {
        BookListResponseDto bookList = bookService.getBookList();
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 목록 가져오기 성공").body(bookList).build(), HttpStatus.OK);
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
