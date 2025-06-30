package ktaivlethminiproject.infra;
import ktaivlethminiproject.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subscribes")
@Transactional
public class SubscribeController {
    @Autowired
    SubscribeRepository subscribeRepository;

    @RequestMapping(value = "/subscribes/borrowbook/{bookId}",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Subscribe borrowBook(@PathVariable Long bookId, HttpServletRequest request, HttpServletResponse response
        ) throws Exception {
            System.out.println("##### /subscribe/borrowBook  called #####");
            
            Long userId = Long.valueOf(request.getHeader("userId"));
            
            Subscribe subscribe = new Subscribe();
            subscribe.setUserId(userId);
            subscribe.setBookId(bookId);
            subscribe.borrowBook();
            subscribeRepository.save(subscribe);
            return subscribe;
    }
    @RequestMapping(value = "/subscribes/ownbook/{bookId}",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Subscribe ownBook(HttpServletRequest request, HttpServletResponse response
        ) throws Exception {
            System.out.println("##### /subscribe/ownBook  called #####");
            
            Long userId = Long.valueOf(request.getHeader("userId"));
            
            Subscribe subscribe = new Subscribe();
            subscribe.setUserId(userId);
            subscribe.setBookId(bookId);
            subscribe.ownBook();
            subscribeRepository.save(subscribe);
            return subscribe;
    }
}
//>>> Clean Arch / Inbound Adaptor
