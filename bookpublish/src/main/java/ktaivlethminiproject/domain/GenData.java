package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.BookpublishApplication;
import ktaivlethminiproject.domain.GenerateRequestCompleted;
import lombok.Data;

@Entity
@Table(name = "GenData_table")
@Data
//<<< DDD / Aggregate Root
public class GenData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private String coverUrl;

    private String summary;

    private String downloadUrl;

    private Integer point;

    private String category;

    private Long userId;

    public static GenDataRepository repository() {
        GenDataRepository genDataRepository = BookpublishApplication.applicationContext.getBean(
            GenDataRepository.class
        );
        return genDataRepository;
    }

    //<<< Clean Arch / Port Method
    public static void requireAccepted(
        PublicationRequested publicationRequested
    ) {
        //implement business logic here:

        // Example 2:  finding and process
        

        repository().findById(publicationRequested.getBookId()).ifPresent(genData->{

            GenerateRequestCompleted evt = new GenerateRequestCompleted(genData); // 생성자에 aggregate 넘겨도 OK
            evt.setBookId(genData.getBookId());
            // evt.setCategory(genData.getCategory());
            evt.setCategory(1);
            evt.setCoverUrl(genData.getCoverUrl());
            evt.setSummary(genData.getSummary());
            evt.setDownloadUrl(genData.getDownloadUrl());
            evt.setPoint(genData.getPoint());
            evt.setRequestSuccessful(true);

            evt.publishAfterCommit();

         });

        

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
