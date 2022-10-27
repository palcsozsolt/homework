package com.pwc.route.service;

import java.util.Collection;

public interface RoutingService {

	/**
	 * This method finds a route between the origin and destination country if exists.
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
	public Collection<String> calculateRoute(String origin, String destination);
}
