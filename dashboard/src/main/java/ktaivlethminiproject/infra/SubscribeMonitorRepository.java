package ktaivlethminiproject.infra;

import java.util.List;
import ktaivlethminiproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscribeMonitors",
    path = "subscribeMonitors"
)
public interface SubscribeMonitorRepository
    extends PagingAndSortingRepository<SubscribeMonitor, Long> {
    List<SubscribeMonitor> findByBookId(Long bookId);
}
