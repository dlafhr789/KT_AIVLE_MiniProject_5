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
    UserRepository userRepository;

    @Autowired
    SubscribeRepository subscribeRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionAccepted'"
    )
    public void wheneverSubscriptionAccepted_DecreasePoint(
        @Payload SubscriptionAccepted subscriptionAccepted
    ) {
        SubscriptionAccepted event = subscriptionAccepted;
        System.out.println(
            "\n\n##### listener DecreasePoint : " +
            subscriptionAccepted +
            "\n\n"
        );

        // Sample Logic //
        User.decreasePoint(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorApproved'"
    )
    public void wheneverAuthorApproved_UpdateRole(
        @Payload AuthorApproved authorApproved
    ) {
        AuthorApproved event = authorApproved;
        System.out.println(
            "\n\n##### listener UpdateRole : " + authorApproved + "\n\n"
        );

        // Sample Logic //
        User.updateRole(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
