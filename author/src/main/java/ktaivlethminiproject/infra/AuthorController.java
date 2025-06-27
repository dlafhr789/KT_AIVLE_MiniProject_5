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

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/authors")
@Transactional
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @RequestMapping(
        value = "/authors/{id}/authorapprove",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Author authorApprove(
        @PathVariable(value = "id") String id,
        @RequestBody AuthorApproveCommand authorApproveCommand,
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
        @PathVariable(value = "id") String id,
        @RequestBody AuthorDenyCommand authorDenyCommand,
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
}
//>>> Clean Arch / Inbound Adaptor
