package voronovo.koi2019.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Score;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ScoreRepository extends PagingAndSortingRepository<Score, Long> {
    @Query(value = "select new Score(min(s.name), min(s.category), min(s.time), s.percent) from Score s where s.category like :category group by lower(s.name), s.percent order by min(s.time) asc, min(s.name) desc",
            countQuery = "select count(s) from Score s")
    Page<Score> findAllByCategoryLikeOrderByTimeAscNameDesc(@RequestParam String category, Pageable pageable);

    Optional<Score> findFirstByNameAndCategoryLikeIgnoreCase(@RequestParam String name, @RequestParam String category);

    @Transactional
    @RestResource(exported = false)
    @Modifying
    @Query("delete from Score where id in (:ids)")
    void deleteAll(@RequestParam List<Long> ids);
}
