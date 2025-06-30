package ktaivlethminiproject.infra;

//import java.util.Optional;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ktaivlethminiproject.service.SubscriptionService;
import ktaivlethminiproject.service.Subscription;
import java.util.List;
import ktaivlethminiproject.config.kafka.KafkaProcessor; // Kafka 채널 인터페이스
import com.fasterxml.jackson.databind.ObjectMapper;   // JSON 변환 도구
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;


    @Autowired(required = false) // SubscriptionService는 없을 수도 있으므로 required = false
    SubscriptionService subscriptionService;
    // 👇 KafkaProcessor를 직접 주입받습니다.
    @Autowired
    KafkaProcessor kafkaProcessor;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping
    public Book saveBook(@RequestBody BookSaved bookSaved) {
        Book book = new Book();
        book.setTitle(bookSaved.getTitle());
        book.setContent(bookSaved.getContent());

        bookRepository.save(book);

        return book;
    }

    @PutMapping("/{id}/openbook")
    public Book openBook(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /books/" + id + "/openbook called #####");

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new Exception("No Entity Found"));
        book.openBook();
        return book;
    }

    @PostMapping("/{id}")
    public void requestPublication(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new Exception("No Entity Found"));
        book.requestPublication();
    }

    // 구독 test용
    @GetMapping("/{id}/test-subscription")
    public String testSubscription(@PathVariable(value = "id") Long id) throws Exception {
        
        // 1. @AllArgsConstructor를 사용하여 불변 객체를 한 번에 생성합니다.
        // (subscriptionId, bookId, userId, userName, state, expiredAt)
        SubscriptionAccepted event = new SubscriptionAccepted(
            1L,          // subscriptionId (임의의 값)
            id,          // bookId from path
            999L,        // userId (테스트용)
            "TestUser",  // userName
            "ACTIVE",    // state
            LocalDateTime.now().plusDays(30) // expiredAt
        );

        // 2. ObjectMapper를 사용해 객체를 JSON 문자열로 변환합니다.
        String jsonMessage = objectMapper.writeValueAsString(event);

        // 3. KafkaProcessor를 이용해 직접 메시지를 발행합니다.
        MessageChannel outputChannel = kafkaProcessor.outboundTopic();
        outputChannel.send(
            MessageBuilder
                .withPayload(jsonMessage)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                // 👇 PolicyHandler가 인식할 수 있도록 type 헤더를 직접 설정! (매우 중요)
                .setHeader("type", "SubscriptionAccepted")
                .build()
        );

        return "SubscriptionAccepted event published for bookId: " + id;
    }
}
