// service/SubscriptionService.java
package ktaivlethminiproject.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

// name: 호출할 마이크로서비스의 이름 (application.yml에 지정된 이름)
// url: 개발 환경에서 직접 주소를 지정할 때 사용 (예: "http://localhost:8082")
@FeignClient(name="subscription", url="${feign.client.url.subscription}")
public interface SubscriptionService {

    // GET /subscriptions?bookId={bookId} API를 호출하겠다는 명세
    @RequestMapping(method = RequestMethod.GET, path = "/subscriptions")
    List<Subscription> getSubscriptionsByBookId(@RequestParam("bookId") Long bookId);
}