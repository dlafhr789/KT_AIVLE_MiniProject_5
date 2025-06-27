package ktaivlethminiproject.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ktaivlethminiproject.config.kafka.KafkaProcessor;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SubscribeMonitorViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscribeMonitorRepository subscribeMonitorRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionAccepted_then_CREATE_1(
        @Payload SubscriptionAccepted subscriptionAccepted
    ) {
        try {
            if (!subscriptionAccepted.validate()) return;

            // view 객체 생성
            SubscribeMonitor subscribeMonitor = new SubscribeMonitor();
            // view 객체에 이벤트의 Value 를 set 함
            subscribeMonitor.setUserId(subscriptionAccepted.getUserId());
            subscribeMonitor.setBookId(subscriptionAccepted.getBookId());
            subscribeMonitor.setUserName(subscriptionAccepted.getUserName());
            // view 레파지 토리에 save
            subscribeMonitorRepository.save(subscribeMonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenIncreasedSubscriber_then_UPDATE_1(
        @Payload IncreasedSubscriber increasedSubscriber
    ) {
        try {
            if (!increasedSubscriber.validate()) return;
            // view 객체 조회

            List<SubscribeMonitor> subscribeMonitorList = subscribeMonitorRepository.findByBookId(
                increasedSubscriber.getId()
            );
            for (SubscribeMonitor subscribeMonitor : subscribeMonitorList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscribeMonitor.setBookTitle(increasedSubscriber.getTitle());
                // view 레파지 토리에 save
                subscribeMonitorRepository.save(subscribeMonitor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
