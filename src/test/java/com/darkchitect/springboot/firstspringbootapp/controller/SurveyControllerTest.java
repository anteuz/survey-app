package com.darkchitect.springboot.firstspringbootapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.darkchitect.springboot.firstspringbootapp.model.Question;
import com.darkchitect.springboot.firstspringbootapp.model.Survey;
import com.darkchitect.springboot.firstspringbootapp.service.SurveyService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=SurveyController.class, secure=false)
public class SurveyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SurveyService service;
	
	
	@Test
	public void retrieveSurveyQuestionTest() throws Exception {
		//setup
		Question mockQuestion = new Question("Question1",
				"Largest Country in the World", "Russia", Arrays.asList(
						"India", "Russia", "United States", "China"));
		when(
				service.retrieveQuestion(Mockito.anyString(), Mockito
						.anyString())).thenReturn(mockQuestion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/surveys/Survey1/questions/Question1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\"}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);	
		}
	
	@Test
	public void getSurveyWithIdTest() throws Exception {
		//setup
					Question question1 = new Question("Question1",
							"Largest Country in the World", "Russia", Arrays.asList(
									"India", "Russia", "United States", "China"));
					Question question2 = new Question("Question2",
							"Most Populus Country in the World", "China", Arrays.asList(
									"India", "Russia", "United States", "China"));
					Question question3 = new Question("Question3",
							"Highest GDP in the World", "United States", Arrays.asList(
									"India", "Russia", "United States", "China"));
					Question question4 = new Question("Question4",
							"Second largest english speaking country", "India", Arrays
									.asList("India", "Russia", "United States", "China"));

					List<Question> questions = new ArrayList<>(Arrays.asList(question1,
							question2, question3, question4));

					Survey survey = new Survey("Survey1", "My Favorite Survey",
							"Description of the Survey", questions);				
				//
				when(service.retrieveSurvey(Mockito.anyString())).thenReturn(survey);

				RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
						"/surveys/Survey1").accept(
						MediaType.APPLICATION_JSON);

				MvcResult result = mockMvc.perform(requestBuilder).andReturn();

				String expected = "{\"id\":\"Survey1\",\"title\":\"My Favorite Survey\",\"description\":\"Description of the Survey\"}";
				
				JSONAssert.assertEquals(expected, result.getResponse()
						.getContentAsString(), false);	
		}
	
	@Test
	public void retrieveQuestionForSurveyTest() throws Exception {
		//setup
		Question question1 = new Question("Question1",
				"Largest Country in the World", "Russia", Arrays.asList(
						"India", "Russia", "United States", "China"));
		Question question2 = new Question("Question2",
				"Most Populus Country in the World", "China", Arrays.asList(
						"India", "Russia", "United States", "China"));
		Question question3 = new Question("Question3",
				"Highest GDP in the World", "United States", Arrays.asList(
						"India", "Russia", "United States", "China"));
		Question question4 = new Question("Question4",
				"Second largest english speaking country", "India", Arrays
						.asList("India", "Russia", "United States", "China"));

		List<Question> questions = new ArrayList<>(Arrays.asList(question1,
				question2, question3, question4));
		
		when(service.retrieveQuestions(Mockito.anyString())).thenReturn(questions);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/surveys/Survey1/questions").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]},{\"id\":\"Question2\",\"description\":\"Most Populus Country in the World\",\"correctAnswer\":\"China\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]},{\"id\":\"Question3\",\"description\":\"Highest GDP in the World\",\"correctAnswer\":\"United States\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]},{\"id\":\"Question4\",\"description\":\"Second largest english speaking country\",\"correctAnswer\":\"India\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]}]";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);	
	}
	
	@Test
	public void getSurveysTest() throws Exception {
		//setup
		List<Survey> surveys = new ArrayList<>();
			Question question1 = new Question("Question1",
					"Largest Country in the World", "Russia", Arrays.asList(
							"India", "Russia", "United States", "China"));
			Question question2 = new Question("Question2",
					"Most Populus Country in the World", "China", Arrays.asList(
							"India", "Russia", "United States", "China"));
			Question question3 = new Question("Question3",
					"Highest GDP in the World", "United States", Arrays.asList(
							"India", "Russia", "United States", "China"));
			Question question4 = new Question("Question4",
					"Second largest english speaking country", "India", Arrays
							.asList("India", "Russia", "United States", "China"));

			List<Question> questions = new ArrayList<>(Arrays.asList(question1,
					question2, question3, question4));

			Survey survey = new Survey("Survey1", "My Favorite Survey",
					"Description of the Survey", questions);

			surveys.add(survey);
		
		
		//
		when(service.retrieveAllSurveys()).thenReturn(surveys);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/surveys").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":\"Survey1\",\"title\":\"My Favorite Survey\",\"description\":\"Description of the Survey\"}]";
		
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);	
		}
	
	@Test
	public void addQuestionToSurveyTest() throws Exception {
		
		Question mockQuestion = new Question("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";
		//surveyService.addQuestion to respond back with mockQuestion
		Mockito.when(
				service.addQuestionToSurvey(Mockito.anyString(), Mockito
						.any(Question.class))).thenReturn(mockQuestion);

		//Send question as body to /surveys/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/surveys/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/surveys/Survey1/questions/1", response
				.getHeader(HttpHeaders.LOCATION));
		
	}
	
}
