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
    private Long bookId;

    private String coverUrl;

    private String summary;

    private String downloadUrl;

    private Integer point;

    private String category;

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

        /** Example 1:  new item 
        GenData genData = new GenData();
        repository().save(genData);

        GenerateRequestCompleted generateRequestCompleted = new GenerateRequestCompleted(genData);
        generateRequestCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(publicationRequested.get???()).ifPresent(genData->{
            
            genData // do something
            repository().save(genData);

            GenerateRequestCompleted generateRequestCompleted = new GenerateRequestCompleted(genData);
            generateRequestCompleted.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
