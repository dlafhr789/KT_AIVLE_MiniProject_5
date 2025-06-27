package ktaivlethminiproject.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ktaivlethminiproject.config.kafka.KafkaProcessor;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BooklistViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BooklistRepository booklistRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookSaved_then_CREATE_1(@Payload BookSaved bookSaved) {
        try {
            if (!bookSaved.validate()) return;

            // view 객체 생성
            Booklist booklist = new Booklist();
            // view 객체에 이벤트의 Value 를 set 함
            booklist.setId(bookSaved.getId());
            booklist.setTitle(bookSaved.getTitle());
            booklist.setAuthor(String.valueOf(bookSaved.getUserId()));
            booklist.setView(Integer.parseInt(bookSaved.getView()));
            booklist.setContent(bookSaved.getContent());
            // view 레파지 토리에 save
            booklistRepository.save(booklist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenGenerateRequestCompleted_then_UPDATE_1(
        @Payload GenerateRequestCompleted generateRequestCompleted
    ) {
        try {
            if (!generateRequestCompleted.validate()) return;
            // view 객체 조회
            Optional<Booklist> booklistOptional = booklistRepository.findById(
                generateRequestCompleted.getBookId()
            );

            if (booklistOptional.isPresent()) {
                Booklist booklist = booklistOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                booklist.setCoverUrl(generateRequestCompleted.getCoverUrl());
                booklist.setSummary(generateRequestCompleted.getSummary());
                booklist.setCategory(
                    String.valueOf(generateRequestCompleted.getCategory())
                );
                booklist.setPoint(generateRequestCompleted.getPoint());
                booklist.setDownloadUrl(
                    generateRequestCompleted.getDownloadUrl()
                );
                // view 레파지 토리에 save
                booklistRepository.save(booklist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookOpened_then_UPDATE_2(@Payload BookOpened bookOpened) {
        try {
            if (!bookOpened.validate()) return;
            // view 객체 조회
            Optional<Booklist> booklistOptional = booklistRepository.findById(
                bookOpened.getId()
            );

            if (booklistOptional.isPresent()) {
                Booklist booklist = booklistOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                booklist.setView(bookOpened.getView());
                // view 레파지 토리에 save
                booklistRepository.save(booklist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPublished_then_UPDATE_3(@Payload Published published) {
        try {
            if (!published.validate()) return;
            // view 객체 조회
            Optional<Booklist> booklistOptional = booklistRepository.findById(
                published.getId()
            );

            if (booklistOptional.isPresent()) {
                Booklist booklist = booklistOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                booklist.setPublishedAt(published.getPublishedAt());
                // view 레파지 토리에 save
                booklistRepository.save(booklist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenIncreasedSubscriber_then_UPDATE_4(
        @Payload IncreasedSubscriber increasedSubscriber
    ) {
        try {
            if (!increasedSubscriber.validate()) return;
            // view 객체 조회
            Optional<Booklist> booklistOptional = booklistRepository.findById(
                increasedSubscriber.getId()
            );

            if (booklistOptional.isPresent()) {
                Booklist booklist = booklistOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                booklist.setSubscribers(increasedSubscriber.getSubscribers());
                // view 레파지 토리에 save
                booklistRepository.save(booklist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
