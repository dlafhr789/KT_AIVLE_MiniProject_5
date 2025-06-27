ddpackage com.example.book;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; // 도서 ID

    private String title; // 도서 제목

    // @Lob
    private String content; // 도서 내용

    private Long userId; // 저자 ID

    private Date publishedAt; // 출간 날짜

    private String state; // 상태

    private Integer view = 0; // 조회수

    private List<Long> subscriber; // 구독자 
}