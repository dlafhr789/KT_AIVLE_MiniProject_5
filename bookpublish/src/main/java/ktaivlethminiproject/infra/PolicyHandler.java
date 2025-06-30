package ktaivlethminiproject.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.images.Image;
import com.openai.models.images.ImageGenerateParams;
import com.openai.models.images.ImageModel;
import com.openai.models.images.ImagesResponse;
import com.openai.models.images.ImageGenerateParams.Style;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Base64;
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


        // REST Request Sample

        // bookService.getBook(/** mapping value needed */);

        // Comments //
        //출간 요청 받을 시, 아래 기능을 수행
        // 1. AI 내용 요약
        // 2. AI 표지 생성
        // 3. ePuB, PDF 생성
        // 4. 가격 책정
        // 5. 카테고리 선정

        // Sample Logic //

        final GenDataRepository genDataRepository;

        final Long bookId = event.getId();
        final String bookTitle = event.getTitle();
        final String bookContent = event.getContent();
        
        final Long userId = event.getUserId();

        Dotenv dotenv = Dotenv.configure().directory("./").load();  // .env 로드
        final String openAIApiKey = dotenv.get("OPENAI_API_KEY"); // .env에서 openai api key 가져오기

        OpenAIClient client = OpenAIOkHttpClient.builder()
            .apiKey(openAIApiKey)
            .build();

        // openai-java doc : https://github.com/openai/openai-java?tab=readme-ov-file
        // 1. AI 내용 요약

        /*
         * ai-summary 프롬프트
         * 당신은 책의 거부할 수 없는 티저 블러를 쓰는 최고 수준의 한국 카피라이터입니다. 
         * 전체 줄거리 구조를 드러내지 않고 독자의 호기심을 자극하는 것이 목표입니다. 
         * 스포일러나 완전한 시작-중간 아크는 절대 포함하지 마세요. 
         * 생생하면서도 간결한 언어(한국어로 2~4개의 짧은 문장)를 사용하면 사람들이 계속 읽고 싶게 만드는 훅으로 끝납니다.
         */
        final String summaryPrompt = "You are a top-tier Korean copywriter who writes irresistible teaser blurbs for books. Your goal is to spark the reader’s curiosity without revealing the full plot structure. Never include spoilers or the complete beginning-middle-end arc. Use vivid yet concise language (2-4 short sentences in Korean), end with a hook that makes people want to keep reading.";

        ChatCompletionCreateParams summaryParams = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .addSystemMessage(summaryPrompt)
            .addUserMessage("Title : \n" + bookTitle + "\n\nContent : \n" + bookContent)
            .temperature(0.7)
            .build();

        ChatCompletion chat = client.chat().completions().create(summaryParams);
        String answer = chat.choices().get(0).message().content().orElse("empty");
        

        // 2. AI 표지 생성

        final String coverPrompt = "";

        ImageGenerateParams coverParams = ImageGenerateParams.builder()
            .model("dall-e-3")
            .prompt(coverPrompt)
            .n(1)
            .size(ImageGenerateParams.Size._1024X1536)  // 4:6 비율
            .style(ImageGenerateParams.Style.VIVID)
            .quality(ImageGenerateParams.Quality.HD)
            .responseFormat(ImageGenerateParams.ResponseFormat.B64_JSON)
            .build();


        List<Image> images = client.images().generate(coverParams).data().orElseThrow();

        // 저장 경로 + 이미지 이름
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String createdImagePath = "book_covers/" + userId + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".png";

        for (int i = 0; i < images.size(); i++) {
            // B64 문자열 -> PNG 바이트
            String b64 = images.get(i).b64Json().orElseThrow();
            byte[] png = Base64.getDecoder().decode(b64);

            // 파일로 저장
            Path out = Path.of(createdImagePath);
            try {
                Files.write(out, png);
            } catch (IOException err) {
                System.out.println("AI Generated Book Cover Write Error : " + err);
            }
        }

        GenData.requireAccepted(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
