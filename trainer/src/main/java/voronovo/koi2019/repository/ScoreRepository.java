package voronovo.koi2019.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Score;

@RepositoryRestResource
public interface ScoreRepository extends PagingAndSortingRepository<Score, Long> {
    Page<Score> findAllByCategoryLike(@RequestParam String category, Pageable pageable);
}
