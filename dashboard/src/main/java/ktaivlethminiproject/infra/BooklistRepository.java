package ktaivlethminiproject.infra;

import java.util.List;
import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "booklists", path = "booklists")
public interface BooklistRepository
    extends PagingAndSortingRepository<Booklist, Long> {}
