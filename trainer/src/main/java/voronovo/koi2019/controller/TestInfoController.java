package voronovo.koi2019.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import voronovo.koi2019.entity.ControllerWrapper;
import voronovo.koi2019.entity.Test;
import voronovo.koi2019.repository.TestRepository;

import java.util.Optional;

@RestController
public class TestInfoController {
    private final TestRepository testRepository;

    public TestInfoController(TestRepository testRepository) {
        this.testRepository = testRepository;
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
}
