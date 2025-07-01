// package ktaivlethminiproject.infra;

// //import java.util.Optional;
// //import javax.servlet.http.HttpServletRequest;
// //import javax.servlet.http.HttpServletResponse;
// import javax.transaction.Transactional;
// import ktaivlethminiproject.domain.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import ktaivlethminiproject.service.SubscriptionService;
// import ktaivlethminiproject.service.Subscription;
// import java.util.List;
// import ktaivlethminiproject.config.kafka.KafkaProcessor; // Kafka 채널 인터페이스
// import com.fasterxml.jackson.databind.ObjectMapper;   // JSON 변환 도구
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.MessageHeaders;
// import org.springframework.messaging.support.MessageBuilder;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.util.MimeTypeUtils;
// import java.time.LocalDateTime;



// @RestController
// @RequestMapping("/books")
// @Transactional
// public class BookController {

//     @Autowired
//     BookRepository bookRepository;


//     @Autowired(required = false) // SubscriptionService는 없을 수도 있으므로 required = false
//     SubscriptionService subscriptionService;
//     // 👇 KafkaProcessor를 직접 주입받습니다.
//     @Autowired
//     KafkaProcessor kafkaProcessor;
//     private static final ObjectMapper objectMapper = new ObjectMapper();


//     @PostMapping
//     public Book saveBook(@RequestBody BookSaved bookSaved) {
//         Book book = new Book();
//         book.setTitle(bookSaved.getTitle());
//         book.setContent(bookSaved.getContent());

//         bookRepository.save(book);

//         return book;
//     }

//     @PutMapping("/{id}/openbook")
//     public Book openBook(@PathVariable(value = "id") Long id) throws Exception {
//         System.out.println("##### /books/" + id + "/openbook called #####");

//         Book book = bookRepository.findById(id)
//                 .orElseThrow(() -> new Exception("No Entity Found"));
//         book.openBook();
//         return book;
//     }

//     @PostMapping("/{id}")
//     public void requestPublication(@PathVariable(value = "id") Long id) throws Exception {
//         Book book = bookRepository.findById(id)
//                 .orElseThrow(() -> new Exception("No Entity Found"));
//         book.requestPublication();
//     }

//     // 구독 test용
//     @GetMapping("/{id}/test-subscription")
//     public String testSubscription(@PathVariable(value = "id") Long id) throws Exception {
        
//         // 1. @AllArgsConstructor를 사용하여 불변 객체를 한 번에 생성합니다.
//         // (subscriptionId, bookId, userId, userName, state, expiredAt)
//         SubscriptionAccepted event = new SubscriptionAccepted(
//             1L,          // subscriptionId (임의의 값)
//             id,          // bookId from path
//             999L,        // userId (테스트용)
//             "TestUser",  // userName
//             "ACTIVE",    // state
//             LocalDateTime.now().plusDays(30) // expiredAt
//         );

//         // 2. ObjectMapper를 사용해 객체를 JSON 문자열로 변환합니다.
//         String jsonMessage = objectMapper.writeValueAsString(event);

//         // 3. KafkaProcessor를 이용해 직접 메시지를 발행합니다.
//         MessageChannel outputChannel = kafkaProcessor.outboundTopic();
//         outputChannel.send(
//             MessageBuilder
//                 .withPayload(jsonMessage)
//                 .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//                 // 👇 PolicyHandler가 인식할 수 있도록 type 헤더를 직접 설정! (매우 중요)
//                 .setHeader("type", "SubscriptionAccepted")
//                 .build()
//         );

//         return "SubscriptionAccepted event published for bookId: " + id;
//     }
// }

package ktaivlethminiproject.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import ktaivlethminiproject.config.kafka.KafkaProcessor;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    KafkaProcessor kafkaProcessor;

    /**
     * 새 책 저장 API
     */
    @PostMapping
    public Book saveBook(@RequestBody BookSaved requestPayload) {
        Book book = new Book();
        book.setTitle(requestPayload.getTitle());
        book.setContent(requestPayload.getContent());
        book.setUserId(requestPayload.getUserId());
        bookRepository.save(book);
        return book;
    }

    /**
     * 책 열람 API
     */
    @PutMapping("/{id}/openbook")
    public Book openBook(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        
        book.openBook();
        
        return book;
    }

    /**
     * 기존 책 출간 요청 API
     */
    @PostMapping("/{id}")
    public void requestPublication(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        book.requestPublication();
    }
    
    /**
     * 임시 테스트용 API: SubscriptionAccepted 이벤트를 강제로 발행
     */
    // @GetMapping("/{id}/test-subscription")
    // public String testSubscription(@PathVariable(value = "id") Long id) {
    //     // 2. 이벤트를 생성하고, 이벤트 스스로 발행하도록 하는 원래의 방식으로 복구
    //     SubscriptionAccepted event = new SubscriptionAccepted();
    //     event.setSubscriptionId(1L);
    //     event.setBookId(id);
    //     event.setUserId(999L);
    //     event.setUserName("TestUser");
    //     event.setState("ACTIVE");
        
    //     event.publish(); // AbstractEvent를 상속받는 DTO의 publish() 메소드 호출

    //     return "SubscriptionAccepted event published for bookId: " + id;
    // }
}