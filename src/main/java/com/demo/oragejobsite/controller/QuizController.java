package com.demo.oragejobsite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.QuizQuestionRepository;
import com.demo.oragejobsite.entity.QuizQuestion;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class QuizController {

    @Autowired
    private QuizQuestionRepository questionRepository;

    
    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/add")
    public ResponseEntity<Object> addQuestion(@RequestBody QuizQuestion question) {
        try {
            questionRepository.save(question);
            return ResponseEntity.status(HttpStatus.OK).body(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add question");
        }
    }
	
    @CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/fetchquestion")
	public ResponseEntity<List<QuizQuestion>> fetchquestion() {
	    try {
	        List<QuizQuestion> questions = questionRepository.findAll();
	        return ResponseEntity.ok(questions);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	
    @CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/deletequestion")
	public ResponseEntity<String> deleteQuestionById(@RequestBody Map<String, Long> request) {
	    Long id = request.get("id");
	    if (id != null) {
	        try {
	            questionRepository.deleteById(id);
	            return ResponseEntity.ok("Question deleted successfully");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the question: " + e.getMessage());
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Invalid request. 'id' parameter is missing or invalid.");
	    }
	}

	

}


