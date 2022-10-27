package com.pwc.route.service;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwc.route.config.CacheConfig.CountryCache;

@Service
public class RoutingServiceImpl implements RoutingService {

	@Autowired
	private CountryCache countryCache;

	public Collection<String> calculateRoute(String origin, String destination) {

		if (origin.equals(destination)) {
			throw new IllegalArgumentException("Invalid request: origin and destination is the same.");
		}

		validateCountry(origin);
		validateCountry(destination);

		return breathFirstRouteSearch(origin, destination);
	}

	private void validateCountry(String countryCca3) {
		Set<String> countryCca3Set = countryCache.getCountryMap().keySet();
		if (!countryCca3Set.contains(countryCca3)) {
			throw new IllegalArgumentException("Invalid request: no neighbour found for " + countryCca3);
		}
	}

	private Collection<String> breathFirstRouteSearch(String origin, String destination) {
		Map<String, String> adjecentCountries = new HashMap<>();
		Set<String> visited = new HashSet<>();
		visited.add(origin);
		Queue<String> queue = new ArrayDeque<>();
		queue.add(origin);

		Map<String, Set<String>> countryMap = countryCache.getCountryMap();
		while (!queue.isEmpty()) {
			String actualCountry = queue.poll();

			Set<String> neighbourCountries = countryMap.get(actualCountry);
			if (neighbourCountries.contains(destination)) {
				adjecentCountries.put(destination, actualCountry);
				return prepareRoute(destination, adjecentCountries);
			}
			for (String neighbourCountry : neighbourCountries) {
				if (!visited.contains(neighbourCountry)) {
					adjecentCountries.put(neighbourCountry, actualCountry);
					visited.add(neighbourCountry);
					queue.add(neighbourCountry);
				}
			}
		}

		throw new IllegalArgumentException(String.format("Invalid request: no route found for origin %s and destination %s", origin, destination));
	}

	private Collection<String> prepareRoute(String destination, Map<String, String> adjecentCountries) {
		Deque<String> route = new ArrayDeque<>();
		route.add(destination);
		String previousCountry = destination;

		while (adjecentCountries.containsKey(previousCountry)) {
			String actualCountry = adjecentCountries.get(previousCountry);
			route.addFirst(actualCountry);
			previousCountry = actualCountry;
		}
		return route;
	}

}
