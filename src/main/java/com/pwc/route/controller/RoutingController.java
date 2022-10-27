package com.pwc.route.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pwc.route.model.RouteResponse;
import com.pwc.route.service.RoutingService;

@RestController
@RequestMapping("/routing")
public class RoutingController {

	private static final Logger LOG = LoggerFactory.getLogger(RoutingController.class);

	@Autowired
	private RoutingService routingService;

	@RequestMapping(method = RequestMethod.GET, value = "/{origin}/{destination}", produces = "application/json")
	public ResponseEntity<RouteResponse> calculateRoute(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {

		LOG.debug(String.format("Request sent with origin %s, destination %s", origin, destination));

		Collection<String> calculatedRoute = routingService.calculateRoute(origin, destination);

		LOG.debug("Returing route response " + calculatedRoute);
		RouteResponse routeResponse = new RouteResponse(calculatedRoute);
		return ResponseEntity.ok().body(routeResponse);
	}
}
