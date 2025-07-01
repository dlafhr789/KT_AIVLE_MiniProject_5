package ktaivlethminiproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import javax.naming.NameParser;
import javax.transaction.Transactional;
import ktaivlethminiproject.config.kafka.KafkaProcessor;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//import java.awt.print.Book;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // AI 생성 완료 시 책을 (생성하고) 출간 상태로 만듦
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GenerateRequestCompleted'"
    )
    public void wheneverGenerateRequestCompleted_Publish(
        @Payload GenerateRequestCompleted generateRequestCompleted
    ) {
        GenerateRequestCompleted event = generateRequestCompleted;
        System.out.println(
            "\n\n##### listener Publish : " + generateRequestCompleted + "\n\n"
        );

        bookRepository.findById(generateRequestCompleted.getId()).ifPresent(book -> {
            book.publish(generateRequestCompleted.getImageUrl());
        });
    }

    // 구독 이벤트 수신 시 구독자 수 증가시킴
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionAccepted'"
    )
    public void wheneverSubscriptionAccepted_Subscribed(
        @Payload SubscriptionAccepted subscriptionAccepted
    ) {
        System.out.println(
            "\n\n##### listener Subscribed : " + subscriptionAccepted + "\n\n"
        );

        bookRepository.findById(subscriptionAccepted.getId()).ifPresent(book -> {
            book.subscribed(subscriptionAccepted.getUserId());  // book.subscribed();
        });
    }
}
