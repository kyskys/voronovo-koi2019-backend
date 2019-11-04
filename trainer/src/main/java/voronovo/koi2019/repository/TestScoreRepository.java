package voronovo.koi2019.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import voronovo.koi2019.entity.TestScore;

import java.util.List;


@RepositoryRestResource
public interface TestScoreRepository extends PagingAndSortingRepository<TestScore, Long> {
    @EntityGraph(value = "TestScore.testItem", type = EntityGraph.EntityGraphType.LOAD)
    List<TestScore> findAllByNameAndTestItem_Test_Id(String name, Long testId);
}
