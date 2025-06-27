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

// openai api
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.images.Image;
import com.openai.models.images.ImageGenerateParams;
import com.openai.models.images.ImageModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

// dotenv
import io.github.cdimascio.dotenv.Dotenv;


//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    GenDataRepository genDataRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublicationRequested'"
    )
    public void wheneverPublicationRequested_RequireAccepted(
        @Payload PublicationRequested publicationRequested
    ) {
        PublicationRequested event = publicationRequested;
        System.out.println(
            "\n\n##### listener RequireAccepted : " +
            publicationRequested +
            "\n\n"
        );

        Dotenv dotenv = Dotenv.configure().directory("./").load();  // .env 로드
        final String openAIApiKey = dotenv.get("OPENAI_API_KEY"); // .env에서 openai api key 가져오기

        // REST Request Sample

        // bookService.getBook(/** mapping value needed */);

        // Comments //
        //출간 요청 받을 시, 아래 기능을 수행
        // 1. AI 표지 생성
        // 2. AI 내용 요약
        // 3. ePuB, PDF 생성
        // 4. 가격 책정
        // 5. 카테고리 선정

        // Sample Logic //

        final GenDataRepository genDataRepository;

        final Long bookId = event.getId();
        final String bookTitle = event.getTitle();
        final String bookContent = event.getContent();
        
        final Long userId = event.getUserId();

        // 1. AI 표지 생성


        GenData.requireAccepted(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
