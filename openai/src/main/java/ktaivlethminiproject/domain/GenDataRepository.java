package ktaivlethminiproject.domain;

import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "genData", path = "genData")
public interface GenDataRepository
    extends PagingAndSortingRepository<GenData, Long> {}
