package com.darkchitect.springboot.firstspringbootapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.darkchitect.springboot.firstspringbootapp.model.Question;
import com.darkchitect.springboot.firstspringbootapp.model.Survey;
import com.darkchitect.springboot.firstspringbootapp.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	private SurveyService service;
	
	/*
	 *Get Mappings for fetching data 
	 */
	@GetMapping("/surveys")
	public List<Survey> getSurveys() {
		return service.retrieveAllSurveys();
	}
	
	@GetMapping("/surveys/{surveyId}")
	public Survey getSurveyWithId(@PathVariable String surveyId) {
		return service.retrieveSurvey(surveyId);
	}
	
	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestionForSurvey(@PathVariable String surveyId) {
		return service.retrieveQuestions(surveyId);
	}
	
	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		return service.retrieveQuestion(surveyId, questionId);
	}
	
	/*
	 * Post methods for creating new data
	 */
	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity addQuestionToSurvey(@PathVariable String surveyId, @RequestBody Question question) {
		
		Question savedQuestion = service.addQuestionToSurvey(surveyId, question);
		
		if(savedQuestion == null) {
			return ResponseEntity.noContent().build();
		}
		
		//return URI of the new resource
		//status = created
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedQuestion.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

}
