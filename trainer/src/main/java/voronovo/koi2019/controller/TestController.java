package voronovo.koi2019.controller;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import voronovo.koi2019.entity.ControllerWrapper;
import voronovo.koi2019.entity.Test;
import voronovo.koi2019.entity.TestItem;
import voronovo.koi2019.repository.TestRepository;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("tests/${testId}/items")
    public ResponseEntity<List<TestItem>> updateTestItems(@RequestBody ControllerWrapper<List<TestItem>> items, @PathVariable Long testId) {
        Optional<Test> optionalTest = testRepository.findById(testId);
        if(optionalTest.isPresent()) {
            Test test = optionalTest.get();
            items.getValue().forEach(item -> item.setTest(test));
            testRepository.save(test);
            return ResponseEntity.ok(items.getValue());
        }
        return ResponseEntity.notFound().build();
    }
}
