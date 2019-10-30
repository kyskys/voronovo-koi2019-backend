package voronovo.koi2019.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import voronovo.koi2019.entity.TestItem;

import java.util.List;

@RepositoryRestResource
@CrossOrigin
public interface TestItemRepository extends PagingAndSortingRepository<TestItem, Long> {
    List<TestItem> findAllByTest_Id(Long id);
}
