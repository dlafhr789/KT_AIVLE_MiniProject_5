package ktaivlethminiproject.infra;

import ktaivlethminiproject.domain.BookSaved;
import ktaivlethminiproject.domain.BookOpened;
import ktaivlethminiproject.view.BookList;
import ktaivlethminiproject.view.BookListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import ktaivlethminiproject.domain.Published;

@Service
public class ViewHandler {

    @Autowired
    private BookListRepository bookListRepository;

    @StreamListener(condition = "headers['type']=='BookSaved'")
    public void whenBookSaved_createBookList(@Payload BookSaved bookSaved) {
        try {
            if (bookSaved.getId() != null) {
                BookList bookList = new BookList();
                bookList.setId(bookSaved.getId());
                bookList.setTitle(bookSaved.getTitle());
                bookList.setView(0);
                bookList.setSubscribers(0);
                // 필요한 다른 정보들을 여기에 설정
                bookListRepository.save(bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(condition = "headers['type']=='BookOpened'")
    public void whenBookOpened_updateView(@Payload BookOpened bookOpened) {
        try {
            if (bookOpened.getId() != null) {
                bookListRepository.findById(bookOpened.getId()).ifPresent(bookList -> {
                    bookList.setView(bookOpened.getView());
                    bookListRepository.save(bookList);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(condition = "headers['type']=='Published'")
    public void whenPublished_updateBookList(@Payload Published published) {
        try {
            if (published.getId() != null) {
                // id로 기존에 생성된 리드 모델 데이터를 찾습니다.
                bookListRepository.findById(published.getId()).ifPresent(bookList -> {
                    // AI가 생성한 imageUrl과 summary로 리드 모델을 업데이트합니다.
                    bookList.setImageUrl(published.getImageUrl());
                    bookList.setSummary(published.getSummary());
                    bookListRepository.save(bookList);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ... 다른 이벤트(예: IncreasedSubscriber)에 대한 리스너도 여기에 추가 ...
}