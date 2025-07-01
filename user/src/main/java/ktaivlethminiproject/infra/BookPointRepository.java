package ktaivlethminiproject.infra;

import java.util.List;
import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "bookPoints",
    path = "bookPoints"
)
public interface BookPointRepository
    extends PagingAndSortingRepository<BookPoint, Long> {}
