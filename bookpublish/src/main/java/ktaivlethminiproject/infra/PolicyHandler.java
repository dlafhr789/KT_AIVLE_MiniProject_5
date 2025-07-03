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
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Base64;

// itextpdf
// import com.itextpdf.kernel.pdf.PdfWriter;
// import com.itextpdf.kernel.pdf.PdfDocument;
// import com.itextpdf.layout.Document;
// import com.itextpdf.layout.element.Paragraph;

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
    @Transactional
    public void wheneverPublicationRequested_RequireAccepted(
        @Payload PublicationRequested publicationRequested
    ) throws IOException {
        PublicationRequested event = publicationRequested;
        System.out.println(
            "\n\n##### listener RequireAccepted : " +
            publicationRequested +
            "\n\n"
        );

        // Comments //
        //출간 요청 받을 시, 아래 기능을 수행
        // 1. AI 내용 요약
        // 2. AI 표지 생성
        // 3. ePuB, PDF 생성
        // 4. 가격 책정
        // 5. 카테고리 선정

        // Sample Logic //

        // GenDataRepository genDataRepository;

        final Long bookId = event.getBookId();
        final String bookTitle = event.getTitle();
        final String bookContent = event.getContent();
        
        final Long userId = event.getUserId();

        GenData genData = new GenData();
        genData.setBookId(bookId);

        Dotenv dotenv = Dotenv.configure().directory("./").load();  // .env 로드
        final String openAIApiKey = dotenv.get("OPENAI_API_KEY"); // .env에서 openai api key 가져오기

        OpenAIClient client = OpenAIOkHttpClient.builder()
            .apiKey(openAIApiKey)
            .build();

        // =======================
        // openai-java doc : https://github.com/openai/openai-java?tab=readme-ov-file
        // 1. AI 내용 요약
        // =======================

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

        ChatCompletion summaryChat = client.chat().completions().create(summaryParams);
        String summaryAnswer = summaryChat.choices().get(0).message().content().orElse("empty");

        genData.setSummary(summaryAnswer);
        System.out.println("Generated Book Summary : " + summaryAnswer);

        // =======================
        // 2. AI 표지 생성
        // =======================

        final String coverPrompt = "다음 도서의 제목과 내용에 맞는 표지를 생성하라.";

        ImageGenerateParams coverParams = ImageGenerateParams.builder()
            .model("dall-e-3")
            .prompt(coverPrompt + "\n\n title : " + bookTitle + "\ncontent :\n" + bookContent)
            .n(1)
            .size(ImageGenerateParams.Size._1024X1792)  // 4:6 비율
            .style(ImageGenerateParams.Style.VIVID)
            .quality(ImageGenerateParams.Quality.HD)
            .responseFormat(ImageGenerateParams.ResponseFormat.B64_JSON)
            .build();


        List<Image> images = client.images().generate(coverParams).data().orElseThrow();

        // 저장 경로 + 이미지 이름
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String name = userId + "_" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String createDir = "book_covers";
        String fileName = name + ".png";
        String createdImagePath = createDir + "/" + fileName;


        for (int i = 0; i < 1; i++) {
            // B64 문자열 -> PNG 바이트
            String b64 = images.get(i).b64Json().orElseThrow();
            byte[] png = Base64.getDecoder().decode(b64);

            // 파일로 저장
            Path out = Path.of(createDir, fileName);
            try {
                Files.createDirectories(out.getParent());
            } catch (IOException e) {
                throw new IllegalStateException("표지 저장용 디렉터리 생성 실패" + e);
            }

            try {
                Files.write(out, png);
            } catch (IOException err) {
                System.out.println("AI Generated Book Cover Write Error : " + err);
            }
        }
        genData.setCoverUrl(createdImagePath);

        // =======================
        // 3. AI 가격 책정
        // =======================
        final String pricingPrompt = "다음 도서의 제목과 내용을 보고 가격을 책정하세요. 반드시 100 - 1000 사이의 정수 하나만 출력해야합니다. 단위, 쉼표, 기호, 텍스트 절대 금지.";

        ChatCompletionCreateParams pricingParams = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .addSystemMessage(pricingPrompt)
            .addUserMessage("Title : \n" + bookTitle + "\n\nContent : \n" + bookContent)
            .temperature(0.7)
            .build();

        ChatCompletion pricingChat = client.chat().completions().create(pricingParams);
        String pricingAnswer = pricingChat.choices().get(0).message().content().orElse("1000");
        
        pricingAnswer = pricingAnswer.replaceAll("[^0-9]", ""); // 숫자 아닌 값은 필터링

        genData.setPoint(Integer.parseInt(pricingAnswer));
        System.out.println("Generated Book Price : " + pricingAnswer);

        // =======================
        // 4. ai 카테고리 선정
        // =======================
        final String categoryPrompt = "다음 도서를 보고 주어진 카테고리 중 적합한 카테고리를 선택하세요. '철학', '종교', '사회과학', '자연과학', '기술과학', '예술', '언어', '문학', '역사' 중 하나를 선택해야하며 반드시 주어진 카테고리만 답변해야 합니다. 예를 들어 사회과학 카테고리를 선택한 경우, '사회과학 카테고리에 적합합니다' 라고 답변하면 안되고 '사회과학' 이라고만 답변해야합니다. 이외의 말이나 띄어쓰기 기호 등은 절대 금지합니다.";

        ChatCompletionCreateParams categoryParams = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .addSystemMessage(categoryPrompt)
            .addUserMessage("Title : \n" + bookTitle + "\n\nContent : \n" + bookContent)
            .temperature(0.7)
            .build();

        ChatCompletion categoryChat = client.chat().completions().create(categoryParams);
        String categoryAnswer = categoryChat.choices().get(0).message().content().orElse("empty");

        genData.setCategory(categoryAnswer);
        System.out.println("Generated Book Category : " + categoryAnswer);

        // =======================
        // 5. PDF 생성
        // =======================

        // String pdfPath = "bookpublish/covers/" + name + ".pdf";  // ← 점 추가!!!

        // // 안전하게 폴더도 보장 + 자원 자동 close
        // Files.createDirectories(Paths.get("bookpublish", "covers"));
        // try (   PdfWriter   writer = new PdfWriter(pdfPath);
        //         PdfDocument pdf    = new PdfDocument(writer);
        //         Document    doc    = new Document(pdf) ) {

        //     doc.add(new Paragraph(bookContent));
        // }

        // genData.setDownloadUrl(pdfPath);

        // =======================
        // 6. 레포 저장
        // =======================

        genDataRepository.save(genData);


        GenData.requireAccepted(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
