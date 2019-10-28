package voronovo.koi2019.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import voronovo.koi2019.entity.ControllerWrapper;
import voronovo.koi2019.repository.ScoreRepository;

import java.util.List;

@RestController
@RequestMapping("scores")
public class ScoreController {
    private final ScoreRepository scoreRepository;

    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @PostMapping("deleteAll")
    public void deleteAll(@RequestBody ControllerWrapper<List<Long>> request) {
        scoreRepository.deleteAll(request.getValue());
    }
}
