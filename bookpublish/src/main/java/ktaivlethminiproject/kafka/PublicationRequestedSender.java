package ktaivlethminiproject.kafka;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;

import ktaivlethminiproject.domain.PublicationRequested;   // 이벤트 클래스 import

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;

@Component
@RequiredArgsConstructor
public class PublicationRequestedSender implements CommandLineRunner {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "ktaivlethminiproject";   // Policy가 듣는 토픽!

    @Override
    public void run(String... args) throws Exception {

        // 1) 이벤트 객체 채우기
        PublicationRequested evt = new PublicationRequested();
        evt.setId(1L);
        evt.setTitle("토끼와 거북이");
        evt.setContent("빠른 토끼와 느린 거북이가 경주를 합니다. 토끼는 자신의 빠른 속도를 믿고 거만하게 굴다가 낮잠을 자는 사이, 거북이는 꾸준히 걸어서 결국 토끼를 이기고 경주에서 승리한다는 내용");
        evt.setUserId(42L);

        // 2) 헤더 넣어서 레코드 만들기
        // ProducerRecord<String, Object> rec =
        //         new ProducerRecord<>(TOPIC, evt.getId().toString(), evt);

        // rec.headers().add("type",
        //         "PublicationRequested".getBytes(StandardCharsets.UTF_8));

        Message<PublicationRequested> msg = MessageBuilder
                .withPayload(evt)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.MESSAGE_KEY, evt.getId().toString())
                .setHeader("type", "PublicationRequested")      // ← ★ String!
                .build();


        // 3) 전송!
        kafkaTemplate.send(msg).addCallback(
                ok -> System.out.println("✅ Kafka 전송 성공!"),
                err -> System.out.println("❌ Kafka 전송 실패: " + err.getMessage())
        );
    }
}