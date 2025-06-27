package ktaivlethminiproject.infra;
import ktaivlethminiproject.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/subscribes/subscribe",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Subscribe subscribe(HttpServletRequest request, HttpServletResponse response, 
        ) throws Exception {
            System.out.println("##### /subscribe/subscribe  called #####");
            Subscribe subscribe = new Subscribe();
            subscribe.subscribe();
            subscribeRepository.save(subscribe);
            return subscribe;
    }
}
//>>> Clean Arch / Inbound Adaptor
