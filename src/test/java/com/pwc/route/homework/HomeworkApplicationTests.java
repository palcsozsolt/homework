package com.pwc.route.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pwc.route.model.RouteResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HomeworkApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testValidRequest() {
		String requestPath = "/routing/CZE/ITA";
		ResponseEntity<RouteResponse> response = restTemplate.getForEntity(requestPath, RouteResponse.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		RouteResponse body = response.getBody();
		String routeResult = body.getRoute().toString();

		assertEquals("[CZE, AUT, ITA]", routeResult);
	}

	@Test
	void testInvalidRequest() {
		String invalidCca3 = "QQQQ";
		String requestPath = String.format("/routing/%s/ITA", invalidCca3);
		ResponseEntity<String> response = restTemplate.getForEntity(requestPath, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

		assertEquals("No country found with " + invalidCca3, response.getBody());
	}

	@Test
	void testSameOriginAndDestination() {
		String requestPath = "/routing/ITA/ITA";
		ResponseEntity<RouteResponse> response = restTemplate.getForEntity(requestPath, RouteResponse.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		RouteResponse body = response.getBody();
		String routeResult = body.getRoute().toString();

		assertEquals("[ITA]", routeResult);
	}
}
