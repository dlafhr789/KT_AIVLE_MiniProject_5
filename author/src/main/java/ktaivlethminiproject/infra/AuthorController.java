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
    @RequestMapping(
        value = "/authors-requests",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<Author> register(
            @RequestHeader("X-User-Id") Long userId,    // 헤더
            @Valid @RequestBody AuthorRegisterCommand cmd // 바디
    ) {

        // ① 헤더값을 커맨드나 엔티티에 주입
        //cmd.setUserId(userId); 

        // ② Author 생성
        Author author = new Author();
        author.setUserId(userId);
        author.setPortfolio(cmd.getPortfolio());
        author.setProfile(cmd.getProfile());
        Author saved = authorRepository.save(author);   // @PostPersist 이벤트 발행

        return ResponseEntity.ok(saved);
    }

    // 등록 요청 수정
    @RequestMapping(
        value = "/authors/{id}",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
        )
    public Author updateAuthor(
            @PathVariable Integer id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody AuthorUpdateCommand cmd) {

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        //optionalAuthor.orElseThrow(() -> new Exception("No Entity Found"));
        Author author = optionalAuthor.get();

        // 3. 필드 업데이트 (userId는 건드리지 않음)
        author.setPortfolio(cmd.getPortfolio());
        author.setProfile(cmd.getProfile());

        // 4. 저장 & 반환
        return authorRepository.save(author);
    }


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
