package ktaivlethminiproject;

import ktaivlethminiproject.domain.Book;
import ktaivlethminiproject.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// @Component  // 테스트X -> 주석 처리
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("##### 테스트 데이터 생성 시작 #####");

        // 1. 테스트용 책 두 권을 생성하고 저장합니다.
        Book book1 = new Book();
        book1.setTitle("첫 번째 테스트 책");
        book1.setContent("이것은 테스트용입니다.");
        book1.setUserId(10L); // 저자는 10번 유저
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("두 번째 테스트 책");
        book2.setContent("이것도 테스트용입니다.");
        book2.setUserId(20L); // 저자는 20번 유저
        bookRepository.save(book2);

        // 2. ID가 1인 사용자가 첫 번째 책을 구독했다고 하드코딩합니다.
        //    (실제로는 ID가 1인 책이 됩니다. ID는 DB에서 자동 생성됩니다.)
        book1.subscribed(1L); // 1L = userId가 1인 사용자

        // 3. 변경된 책 정보를 다시 저장합니다.
        bookRepository.save(book1);

        System.out.println("##### 'userId 1'이 '첫 번째 테스트 책'을 구독하는 테스트 데이터 생성 완료 #####");
    }
}