package com.example.junitproject.service;

import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.dto.BookResponseDto;
import com.example.junitproject.dto.BookSaveRequestDto;
import com.example.junitproject.util.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    // 단점 ! -> Service만 테스트하고싶은데 Repository Layer 도 메모리에 올라감! 무거워진다.
    @Test
    void addBook() {
        // given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("junit5");
        dto.setAuthor("Coen90");

        // stub (가설)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
//        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookResponseDto bookRespDto = bookService.addBook(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
    }

}