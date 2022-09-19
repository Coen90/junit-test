package com.example.junitproject.service;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.dto.BookResponseDto;
import com.example.junitproject.dto.BookSaveRequestDto;
import com.example.junitproject.util.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void bookList() {
        // given(파라메터로 들어올 데이터)
        // stub (가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "metacoding"));
        books.add(new Book(2L, "spring강의", "youtube"));
        when(bookRepository.findAll()).thenReturn(books);

        // when (실행)
        List<BookResponseDto> dtos = bookService.getBookList();
        
        // print
        dtos.stream().forEach((dto) -> {
            System.out.println("dto.getId() = " + dto.getId());
            System.out.println("dto.getTitle() = " + dto.getTitle());
            System.out.println();
        });

        // then (검증)
        assertThat(dtos.get(0).getTitle()).isEqualTo("junit강의");
        assertThat(dtos.get(0).getAuthor()).isEqualTo("metacoding");
        assertThat(dtos.get(1).getTitle()).isEqualTo("spring강의");
        assertThat(dtos.get(1).getAuthor()).isEqualTo("youtube");
    }

}