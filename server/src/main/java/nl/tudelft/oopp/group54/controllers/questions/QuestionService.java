package nl.tudelft.oopp.group54.controllers.questions;

import nl.tudelft.oopp.group54.entities.Question;

import nl.tudelft.oopp.group54.entities.QuestionKey;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.List;

public interface QuestionService {
    Map<String, Object> postQuestion(Long lectureId, String userId, String questionText);

    List<Question> getAllQuestions(Long lectureId, String userId);
}