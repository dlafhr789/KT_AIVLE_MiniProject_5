package ktaivlethminiproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequiredArgsConstructor
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    //작가 등록 요청
    // @PostMapping("/authors")
    // public ResponseEntity<Void> register(@Valid @RequestBody AuthorRegisterCommand cmd) {

    //     Author author = new Author(
    //         cmd.getUserId(),
    //         cmd.getPortfolio(),
    //         cmd.getProfile()
    //     );
    //     authorRepository.save(author);   // @PostPersist -> AuthorRegistrationRequested 이벤트 발행

    //     return ResponseEntity.accepted().build();
    // }

    @RequestMapping(
        value = "/authors/{id}/authorapprove",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Author authorApprove(
        @PathVariable(value = "id") Integer id,
        @RequestBody(required = false) AuthorApproveCommand authorApproveCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /author/authorApprove  called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        optionalAuthor.orElseThrow(() -> new Exception("No Entity Found"));
        Author author = optionalAuthor.get();
        author.authorApprove(authorApproveCommand);

        authorRepository.save(author);
        return author;
    }

    @RequestMapping(
        value = "/authors/{id}/authordeny",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Author authorDeny(
        @PathVariable(value = "id") Integer id,
        @RequestBody(required = false) AuthorDenyCommand authorDenyCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /author/authorDeny  called #####");
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        optionalAuthor.orElseThrow(() -> new Exception("No Entity Found"));
        Author author = optionalAuthor.get();
        author.authorDeny(authorDenyCommand);

        authorRepository.save(author);
        return author;
    }

    //ppt 8페이지 관리자 구현
    @GetMapping("/authors/pending")
    public List<Author> getPendingAuthors() {
        return authorRepository.findByState(AuthorState.PENDING);
    }
}
//>>> Clean Arch / Inbound Adaptor
