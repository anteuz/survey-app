package com.darkchitect.springboot.firstspringbootapp.controller;

import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.darkchitect.springboot.firstspringbootapp.FirstSpringbootAppApplication;
import com.darkchitect.springboot.firstspringbootapp.model.Question;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=FirstSpringbootAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

	@LocalServerPort
	private int port;

	HttpHeaders headers = new HttpHeaders();
			
			
	TestRestTemplate template = new TestRestTemplate();

	@Before
	public void before() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", createBasicAuthenticationTest("user1", "secret1"));
	}

	private String createBasicAuthenticationTest(String username, String password) {
		return "Basic " + Base64.encode(String.valueOf(username+":"+password).getBytes(Charset.forName("US-ASCII"))).toString();
	}

	@Test
	public void test() throws JSONException {

		String expected = "{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\",\"options\":[\"India\",\"Russia\",\"United States\",\"China\"]}";

		ResponseEntity<String> response = template.exchange(
				createUrl("/surveys/Survey1/questions/Question1"), 
				HttpMethod.GET,
				new HttpEntity<String>("DUMMY", headers), 
				String.class
				);

		JSONAssert.assertEquals(response.getBody(), expected, false);

	}
	
	@Test
	public void addQuestion() throws JSONException {

		Question questionToAdd = new Question("DUMMYSTUFF", "Greatest person alive?", "Terminator", Arrays.asList("Terminator", "Putinator", "Antti", "He-Man"));

		ResponseEntity<String> response = template.exchange(
				createUrl("/surveys/Survey1/questions"), 
				HttpMethod.POST,
				new HttpEntity<Question>(questionToAdd, headers), 
				String.class
				);

		String location =   response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		
		assertTrue(location.contains("/surveys/Survey1/questions"));
		
	}

	@Test
	public void retrieveSurveyQuestions() {
		
		ResponseEntity<List<Question>> response = template.exchange(
				createUrl("/surveys/Survey1/questions"), 
				HttpMethod.GET,
				new HttpEntity<String>("DUMMY", headers), 
				new ParameterizedTypeReference<List<Question>>() {}
				);

		Question sampleQuestion = new Question("Question1", "Largest Country in the World", "Russia", Arrays.asList("India", "Russia", "United States", "Chine"));
		
		assertTrue(response.getBody().contains(sampleQuestion));
	}
	
	//Helpers
	private String createUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

}
