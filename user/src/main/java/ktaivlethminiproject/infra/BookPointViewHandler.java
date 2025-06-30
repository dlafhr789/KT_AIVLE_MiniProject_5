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
public class BookPointViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private BookPointRepository bookPointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenGenerateRequestCompleted_then_CREATE_1(
        @Payload GenerateRequestCompleted generateRequestCompleted
    ) {
        try {
            if (!generateRequestCompleted.validate()) return;

            // view 객체 생성
            BookPoint bookPoint = new BookPoint();
            // view 객체에 이벤트의 Value 를 set 함
            bookPoint.setId(generateRequestCompleted.getBook_id());
            bookPoint.setPoint(generateRequestCompleted.getPoint());
            // view 레파지 토리에 save
            bookPointRepository.save(bookPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
