package com.example.junitproject.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("dev")
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

//    @BeforeAll // 테스트 시작 전 한번만 실행
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

    // 1. 책 등록
    @Test
    void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "코엔";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 2. 책 목록보기
    @Test
    void 책목록보기_test() {
        // given
        String title = "junit5";
        String author = "코엔";

        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }

    // 3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책한건보기_test() {
        // given
        String title = "junit5";
        String author = "코엔";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책삭제_test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    void 책수정_test() {
        // given
        Long id = 1L;
        String title = "제이유닛파이브";
        String author = "Coen90";
        Book book = new Book(id, title, author);

        // when
//        bookRepository.findAll().stream()
//                .forEach(b -> {
//                    System.out.println("book = " + b.getId());
//                    System.out.println("book = " + b.getTitle());
//                    System.out.println("book = " + b.getAuthor());
//                    System.out.println("=================================");
//                });
        Book bookPS = bookRepository.save(book);
//        bookRepository.findAll().stream()
//                .forEach(b -> {
//                    System.out.println("book = " + b.getId());
//                    System.out.println("book = " + b.getTitle());
//                    System.out.println("book = " + b.getAuthor());
//                    System.out.println("=================================");
//                });

        // then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }
}