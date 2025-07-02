package ktaivlethminiproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
//import ktaivlethminiproject.domain.*;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBySubscriberIdsContaining(Long userId);

    // 구독자(Subscribers) 순으로 내림차순(Desc) 정렬하여 상위 5개(Top5)를 조회하는 메소드
    List<Book> findTop5ByOrderBySubscribersDesc();
}
