package voronovo.koi2019.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Question;
import voronovo.koi2019.entity.Score;

import java.util.Date;

@RepositoryRestResource
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    Question findFirstByCreatedAtBeforeOrderByCreatedAtDesc(@RequestParam Date beforeDate);
}
