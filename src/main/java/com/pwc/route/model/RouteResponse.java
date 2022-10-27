package com.pwc.route.model;

import java.util.List;

public class RouteResponse {

	private List<String> route;

	public RouteResponse() {
	}

	public RouteResponse(List<String> route) {
		this.route = route;
	}

	public List<String> getRoute() {
		return route;
	}

}
