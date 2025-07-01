package ktaivlethminiproject.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource; // import 추가

@RepositoryRestResource(collectionResourceRel="booklists", path="booklists") // 어노테이션 추가
public interface BookListRepository extends JpaRepository<BookList, Long> {
}