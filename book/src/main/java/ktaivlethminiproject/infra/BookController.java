package ktaivlethminiproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping
    public Book saveBook(@RequestBody BookSaved requestPayload) {
        Book book = new Book();
        // book.setId(requestPayload.getId());
        book.setTitle(requestPayload.getTitle());
        book.setContent(requestPayload.getContent());
        book.setUserId(requestPayload.getUserId());

        bookRepository.save(book);

        return book;
    }

    @PutMapping("/{id}/openbook")
    public Book openBook(@PathVariable(value = "id") Long id) throws Exception {
        System.out.println("##### /book/openBook  called #####");

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
}
