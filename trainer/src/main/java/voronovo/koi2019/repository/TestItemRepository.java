package voronovo.koi2019.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import voronovo.koi2019.entity.TestItem;

import java.util.List;

public interface TestItemRepository extends PagingAndSortingRepository<TestItem, Long> {
    List<TestItem> findAllByTest_Id(Long id);
}
