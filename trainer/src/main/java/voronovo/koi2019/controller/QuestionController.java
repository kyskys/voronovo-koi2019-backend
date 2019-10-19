package voronovo.koi2019.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import voronovo.koi2019.entity.Question;
import voronovo.koi2019.repository.QuestionRepository;

import java.util.Optional;

@RepositoryRestController
public class QuestionController {
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions/search/updateQuestionWinner")
    public ResponseEntity<Boolean> updateQuestionWinner(Long id, String winner) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            if (question.getWinner() == null) {
                question.setWinner(winner);
                questionRepository.save(question);
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
