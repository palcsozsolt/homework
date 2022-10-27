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
class RoutingControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testValidRequestShort() {
		String requestPath = "/routing/CZE/ITA";
		ResponseEntity<RouteResponse> response = restTemplate.getForEntity(requestPath, RouteResponse.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		RouteResponse body = response.getBody();
		String routeResult = body.getRoute().toString();

		assertEquals("[CZE, AUT, ITA]", routeResult);
	}

	@Test
	void testValidRequestLong() {
		String requestPath = "/routing/PRT/KHM";
		ResponseEntity<RouteResponse> response = restTemplate.getForEntity(requestPath, RouteResponse.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		RouteResponse body = response.getBody();
		String routeResult = body.getRoute().toString();

		assertEquals("[PRT, ESP, FRA, DEU, POL, RUS, CHN, LAO, KHM]", routeResult);
	}

	@Test
	void testInvalidRequestNoRoute() {
		String cca3From = "PRT";
		String cca3To = "USA";
		String requestPath = String.format("/routing/%s/%s", cca3From, cca3To);
		ResponseEntity<String> response = restTemplate.getForEntity(requestPath, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

		assertEquals(String.format("Invalid request: no route found for origin %s and destination %s", cca3From, cca3To), response.getBody());
	}

	@Test
	void testInvalidRequestNoNeighbourForOrigin() {
		String cca3 = "ABW";
		String requestPath = String.format("/routing/%s/ITA", cca3);
		ResponseEntity<String> response = restTemplate.getForEntity(requestPath, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

		assertEquals("Invalid request: no neighbour found for " + cca3, response.getBody());
	}

	@Test
	void testInvalidRequestNoNeighbourForDestination() {
		String cca3 = "ABW";
		String requestPath = String.format("/routing/ITA/%s", cca3);
		ResponseEntity<String> response = restTemplate.getForEntity(requestPath, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

		assertEquals("Invalid request: no neighbour found for " + cca3, response.getBody());
	}

	@Test
	void testInvalidRequestSameOriginAndDestination() {
		String requestPath = "/routing/ITA/ITA";
		ResponseEntity<String> response = restTemplate.getForEntity(requestPath, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

		assertEquals("Invalid request: origin and destination is the same.", response.getBody());
	}

}
