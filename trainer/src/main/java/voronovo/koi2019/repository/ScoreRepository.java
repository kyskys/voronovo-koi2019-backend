package voronovo.koi2019.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import voronovo.koi2019.entity.Score;

@RepositoryRestResource(collectionResourceRel = "score", path = "score", itemResourceRel = "score")
public interface ScoreRepository extends PagingAndSortingRepository<Score, Long> {

}
