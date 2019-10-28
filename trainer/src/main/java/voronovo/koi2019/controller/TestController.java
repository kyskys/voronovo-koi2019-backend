package voronovo.koi2019.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import voronovo.koi2019.entity.Test;
import voronovo.koi2019.repository.TestRepository;

@RepositoryRestController
public class TestController {
    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @PostMapping("tests")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        test.getItems().forEach(testItem -> testItem.setTest(test));
        testRepository.save(test);
        return ResponseEntity.ok(test);
    }
}
