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
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    KafkaProcessor kafkaProcessor;

    // 도서 저장 API
    @PostMapping
    public Book saveBook(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody BookSaved requestPayload
    ) {
        Book book = new Book();
        book.setTitle(requestPayload.getTitle());
        book.setContent(requestPayload.getContent());
        book.setUserId(userId);
        bookRepository.save(book);

        System.out.println("### Book" + book.getBookId() + "saved ###");

        return book;
    }

    // 도서 삭제 API
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        bookRepository.delete(book);
        System.out.println("### Book" + id + "deleted ###");
    }

    // 도서 열람 API
    @PutMapping("/{id}/openbook")
    public Book openBook(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        
        book.openBook();
        return bookRepository.save(book);
    }

    // 도서 읽기 API
    @GetMapping("/{id}/read")
    public Map<String, Object> readBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        Map<String, Object> result = new HashMap<>();
        result.put("title", book.getTitle());
        result.put("content", book.getContent());
        result.put("userId", book.getUserId());

        return result;
    }

    // 출간 요청 API
    @PostMapping("/{id}")
    public void requestPublication(@PathVariable(value = "id") Long id) throws Exception {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new Exception("No Entity Found"));
        book.requestPublication();
    }
    
    // 도서 목록 API
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 구독 도서 API
    @GetMapping("/search/my-subscriptions")
    public List<Book> getSubscribedBooks(@RequestParam("userId") Long userId) {
        return bookRepository.findBySubscriberIdsContaining(userId);
    }

    // 베스트셀러 API
    @GetMapping("/bestsellers")
    public List<Book> getBestsellers() {
        return bookRepository.findTop5ByOrderBySubscribersDesc();
    }
}