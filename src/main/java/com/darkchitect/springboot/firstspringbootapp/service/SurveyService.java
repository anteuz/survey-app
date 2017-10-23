package com.darkchitect.springboot.firstspringbootapp.service;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.darkchitect.springboot.firstspringbootapp.model.Question;
import com.darkchitect.springboot.firstspringbootapp.model.Survey;


@Component
public class SurveyService {
	private static final String CHINA = "China";
	private static final String UNITEDSTATES = "United States";
	private static final String RUSSIA = "Russia";
	private static final String INDIA = "India";
	private static List<Survey> surveys = new ArrayList<>();
	static {
		Question question1 = new Question("Question1",
				"Largest Country in the World", RUSSIA, Arrays.asList(
						INDIA, RUSSIA, UNITEDSTATES, CHINA));
		Question question2 = new Question("Question2",
				"Most Populus Country in the World", CHINA, Arrays.asList(
						INDIA, RUSSIA, UNITEDSTATES, CHINA));
		Question question3 = new Question("Question3",
				"Highest GDP in the World", UNITEDSTATES, Arrays.asList(
						INDIA, RUSSIA, UNITEDSTATES, CHINA));
		Question question4 = new Question("Question4",
				"Second largest english speaking country", INDIA, Arrays
						.asList(INDIA, RUSSIA, UNITEDSTATES, CHINA));

		List<Question> questions = new ArrayList<>(Arrays.asList(question1,
				question2, question3, question4));

		Survey survey = new Survey("Survey1", "My Favorite Survey",
				"Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> retrieveAllSurveys() {
		return surveys;
	}

	public Survey retrieveSurvey(String surveyId) {
		for (Survey survey : surveys) {
			if (survey.getId().equals(surveyId)) {
				return survey;
			}
		}
		return null;
	}

	public List<Question> retrieveQuestions(String surveyId) {
		Survey survey = retrieveSurvey(surveyId);

		if (survey == null) {
			return Collections.emptyList();
		}

		return survey.getQuestions();
	}

	public Question retrieveQuestion(String surveyId, String questionId) {
		Survey survey = retrieveSurvey(surveyId);

		if (survey == null) {
			return null;
		}

		for (Question question : survey.getQuestions()) {
			if (question.getId().equals(questionId)) {
				return question;
			}
		}

		return null;
	}

	private SecureRandom random = new SecureRandom();

	public Question addQuestionToSurvey(String surveyId, Question question) {
		Survey survey = retrieveSurvey(surveyId);
		
		if(survey == null) {
			return null;
		}
		
		String randomId = new BigInteger(130, random).toString(32);
		question.setId(randomId);
		
		survey.getQuestions().add(question);
		
		return question;
	}
}