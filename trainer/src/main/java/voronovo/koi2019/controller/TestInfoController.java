package voronovo.koi2019.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voronovo.koi2019.entity.ControllerWrapper;
import voronovo.koi2019.entity.Test;
import voronovo.koi2019.entity.TestItem;
import voronovo.koi2019.entity.TestScore;
import voronovo.koi2019.repository.TestRepository;
import voronovo.koi2019.repository.TestScoreRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TestInfoController {
    private final TestRepository testRepository;
    private final TestScoreRepository testScoreRepository;

    public TestInfoController(TestRepository testRepository, TestScoreRepository testScoreRepository) {
        this.testRepository = testRepository;
        this.testScoreRepository = testScoreRepository;
    }


    @GetMapping("tests/{id}/time")
    public ResponseEntity<ControllerWrapper<Long>> getRemainingTime(@PathVariable Long id) {
        ControllerWrapper<Long> result = new ControllerWrapper<>();
        Optional<Test> optionalTest = testRepository.findById(id);
        optionalTest.ifPresent(test -> {
            if (test.isActive() && test.getStartedAt() != null & test.getTimeToComplete() != null) {
                if (test.getStartedAt().getTime() + test.getTimeToComplete() > System.currentTimeMillis()) {
                    result.setValue(test.getStartedAt().getTime() + test.getTimeToComplete() - System.currentTimeMillis());
                } else {
                    result.setValue(0L);
                }
            }
        });
        return ResponseEntity.ok(result);
    }

    @PostMapping("tests/deleteAll")
    public void deleteAll(@RequestBody ControllerWrapper<List<Long>> request) {
        testRepository.deleteAll(request.getValue());
    }

    @PostMapping("tests/{testId}/name/{name}")
    public ResponseEntity addTestResultsForName(@PathVariable Long testId,
                                                @PathVariable String name,
                                                @RequestBody Map<Long, String> answers) {
        answers.entrySet().stream()
                .map(entry -> new TestScore(name, new TestItem(entry.getKey()), entry.getValue()))
                .forEach(testScoreRepository::save);
        return ResponseEntity.ok().build();
    }

    @GetMapping("tests/{testId}/name/{name}")
    public ResponseEntity<List<TestScore>> getTestResultsForName(@PathVariable Long testId,
                                                                 @PathVariable String name) {
        return ResponseEntity.ok(testScoreRepository.findAllByNameAndTestItem_Test_Id(name, testId));
    }
}
