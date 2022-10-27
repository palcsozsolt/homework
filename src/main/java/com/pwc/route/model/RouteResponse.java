package com.pwc.route.model;

import java.util.Collection;

public class RouteResponse {

	private Collection<String> route;

	public RouteResponse() {
	}

	public RouteResponse(Collection<String> route) {
		this.route = route;
	}

	public Collection<String> getRoute() {
		return route;
	}

}
