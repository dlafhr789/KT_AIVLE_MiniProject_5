package ktaivlethminiproject.domain;

import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository
    extends PagingAndSortingRepository<Book, Long> {}
