package voronovo.koi2019.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Test;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource
public interface TestRepository extends PagingAndSortingRepository<Test, Long> {
    @Transactional
    @RestResource(exported = false)
    @Modifying
    @Query("delete from Test where id in (:ids)")
    void deleteAll(@RequestParam List<Long> ids);
}
