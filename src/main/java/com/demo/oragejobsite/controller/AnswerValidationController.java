package com.demo.oragejobsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.QuizQuestionRepository;
import com.demo.oragejobsite.entity.QuizQuestion;
import com.demo.oragejobsite.entity.UserAnswer;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AnswerValidationController {
	
	 private final QuizQuestionRepository quizQuestionRepository;

	    @Autowired
	    public AnswerValidationController(QuizQuestionRepository quizQuestionRepository) {
	        this.quizQuestionRepository = quizQuestionRepository;
	    }
	    
	    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/checkallanswer")
    public ResponseEntity<Boolean> validateAnswers(@RequestBody List<UserAnswer> userAnswers) {
        // Assuming you have a list of UserAnswer objects representing each question, user answer, and correct answer
        System.out.println(userAnswers.get(0).getQuestionId());
        // Implement the logic to compare userAnswers with correct answers
        boolean allCorrect = checkAllAnswers(userAnswers);
        
        // Return true if all answers are correct, else false
        return ResponseEntity.ok(allCorrect);
    }
    
    private boolean checkAllAnswers(List<UserAnswer> userAnswers) {
    	
    	System.out.println("Received userAnswers: " + userAnswers);
        for (UserAnswer answer : userAnswers) {
            Long questionId = answer.getQuestionId();
            String userResponse = answer.getUserResponse();
            // Retrieve the correct answer based on questionId from the database
            String correctAnswer = getCorrectAnswerFromDatabase(questionId);

            if (!userResponse.equalsIgnoreCase(correctAnswer)) {
                return false;
            }
        }

        return true;
    }

    private String getCorrectAnswerFromDatabase(Long questionId) {
        // Fetch the correct answer from the database using the QuizQuestionRepository
        QuizQuestion quizQuestion = quizQuestionRepository.findById((long) questionId).orElse(null);

        return (quizQuestion != null) ? quizQuestion.getCorrectAnswer() : "";
    }
    
    
}
