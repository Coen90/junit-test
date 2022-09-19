package com.example.junitproject.service;

import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.dto.BookResponseDto;
import com.example.junitproject.dto.BookSaveRequestDto;
import com.example.junitproject.util.MailSenderStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    // 단점 ! -> Service만 테스트하고싶은데 Repository Layer 도 메모리에 올라감! 무거워진다.
    @Test
    void addBook() {
        // given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("junit5");
        dto.setAuthor("Coen90");

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();
        // 가짜로 bookRepository 만들기!!

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResponseDto bookRespDto = bookService.addBook(dto);

        // then
        assertEquals(dto.getTitle(), bookRespDto.getTitle());
        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());

    }

}