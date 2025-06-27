package ktaivlethminiproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import ktaivlethminiproject.config.kafka.KafkaProcessor;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='GenerateSucceeded'"
    )
    public void wheneverGenerateSucceeded_Publish(
        @Payload GenerateSucceeded generateSucceeded
    ) {
        GenerateSucceeded event = generateSucceeded;
        System.out.println(
            "\n\n##### listener Publish : " + generateSucceeded + "\n\n"
        );

        // Sample Logic //
        Book.publish(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionAccepted'"
    )
    public void wheneverSubscriptionAccepted_Subscribed(
        @Payload SubscriptionAccepted subscriptionAccepted
    ) {
        SubscriptionAccepted event = subscriptionAccepted;
        System.out.println(
            "\n\n##### listener Subscribed : " + subscriptionAccepted + "\n\n"
        );

        // Sample Logic //
        Book.subscribed(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
