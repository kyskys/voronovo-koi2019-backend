package voronovo.koi2019.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Score;

import java.util.Optional;

@RepositoryRestResource
public interface ScoreRepository extends PagingAndSortingRepository<Score, Long> {
    Page<Score> findAllByCategoryLikeOrderByTimeAscNameDesc(@RequestParam String category, Pageable pageable);

    Optional<Score> findFirstByNameAndCategoryLikeIgnoreCase(@RequestParam String name, @RequestParam String category);
}
