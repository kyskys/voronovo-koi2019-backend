package voronovo.koi2019.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import voronovo.koi2019.entity.Question;

import java.util.Date;

@RepositoryRestResource
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    Question findFirstByCreatedAtBeforeOrderByCreatedAtDesc(@RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                    Date beforeDate);

    @Modifying
    @Query("update Question q set q.winner=:winner where id=:id")
    void updateQuestionWinner(Long id, String winner);
}
