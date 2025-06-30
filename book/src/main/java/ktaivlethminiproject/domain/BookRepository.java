package ktaivlethminiproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
//import ktaivlethminiproject.domain.*;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {}
