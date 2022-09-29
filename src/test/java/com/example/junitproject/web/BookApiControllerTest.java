package com.example.junitproject.web;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.service.BookService;
import com.example.junitproject.web.dto.request.BookSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private BookRepository bookRepository;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach // 각 테스트 시작 전 한번씩 실행
    public void 데이터준비() {
        String title = "junit5";
        String author = "코엔";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }

//    @Test
//    void di_test() {
//        if (bookService == null) {
//            System.out.println("It is null ");
//        } else {
//            System.out.println("It is NOT null");
//        }
//    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    void getBookList_test() {
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/books", HttpMethod.GET, request, String.class);

        System.out.println(response.getBody());
        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.items[0].title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");
    }

    @Test
    void saveBook_test() throws Exception {
        // given
        BookSaveRequestDto bookSaveRequestDto = new BookSaveRequestDto();
        bookSaveRequestDto.setTitle("Title  test");
        bookSaveRequestDto.setAuthor("Author test");

        String body = om.writeValueAsString(bookSaveRequestDto);
        System.out.println("===================================================");
        System.out.println(body);
        System.out.println("===================================================");

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(bookSaveRequestDto.getTitle()).isEqualTo(title);
        assertThat(bookSaveRequestDto.getAuthor()).isEqualTo(author);
    }

}