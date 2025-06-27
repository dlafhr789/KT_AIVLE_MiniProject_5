package ktaivlethminiproject.domain;

import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscribes",
    path = "subscribes"
)
public interface SubscribeRepository
    extends PagingAndSortingRepository<Subscribe, Long> {}
