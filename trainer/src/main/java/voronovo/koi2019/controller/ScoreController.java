package voronovo.koi2019.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import voronovo.koi2019.entity.Score;
import voronovo.koi2019.repository.ScoreRepository;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class ScoreController {
    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @PostMapping("scores")
    public ResponseEntity<Score> save(@RequestBody Score newScore) {
        Score result;
        Optional<Score> optionalScore = scoreRepository.findFirstByNameAndCategoryLikeIgnoreCase(newScore.getName(), "%" + newScore.getCategory() + "%");
        if (optionalScore.isPresent()) {
            Score score = optionalScore.get();
            if (newScore.getTime() < score.getTime()) {
                score.setTime(newScore.getTime());
                score.setPercent(newScore.getPercent());
                score.setDate(newScore.getDate());
            }
            result = score;
        } else {
            scoreRepository.save(newScore);
            result = newScore;
        }
        scoreRepository.save(result);
        Resource<Score> resources = new Resource<>(result);
        resources.add(linkTo(methodOn(ScoreController.class).save(result)).withSelfRel());
        return ResponseEntity.ok(result);
    }
}
