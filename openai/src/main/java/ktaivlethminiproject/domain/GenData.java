package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.OpenaiApplication;
import ktaivlethminiproject.domain.GenerateFailed;
import ktaivlethminiproject.domain.GenerateSucceeded;
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
        GenDataRepository genDataRepository = OpenaiApplication.applicationContext.getBean(
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

        GenerateSucceeded generateSucceeded = new GenerateSucceeded(genData);
        generateSucceeded.publishAfterCommit();
        GenerateFailed generateFailed = new GenerateFailed(genData);
        generateFailed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(publicationRequested.get???()).ifPresent(genData->{
            
            genData // do something
            repository().save(genData);

            GenerateSucceeded generateSucceeded = new GenerateSucceeded(genData);
            generateSucceeded.publishAfterCommit();
            GenerateFailed generateFailed = new GenerateFailed(genData);
            generateFailed.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
