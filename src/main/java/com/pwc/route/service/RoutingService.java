package com.pwc.route.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class RoutingService {

	// TODO: temp set
	private final Set<String> countries = Set.of("CZE", "AUT", "ITA");

	public List<String> calculateRoute(String origin, String destination) {

		validateCountry(origin);
		validateCountry(destination);

		if (origin.equals(destination)) {
			return List.of(origin);
		}

		return List.of("CZE", "AUT", "ITA");
	}

	private void validateCountry(String countryCca3) {
		if (!countries.contains(countryCca3)) {
			throw new IllegalArgumentException("No country found with " + countryCca3);
		}
	}
}
