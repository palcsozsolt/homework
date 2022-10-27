package com.pwc.route.config;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pwc.route.model.Country;

@Configuration
public class CacheConfig {

	// In the task description it is not detailed when to handle the country data.
	// It is cached at the startup, but alternatively:
	// - it could be fetched on every single request or
	// - when a new request received cache the data (if cache is empty) and clear cache periodically (daily/weekly).
	@Bean
	public CountryCache countryCacheService(@Value("${pwc-service.countries.url}") String countriesUrl) {
		String responseBody = new RestTemplate().getForEntity(countriesUrl, String.class).getBody();
		List<Country> countryList = new Gson().fromJson(responseBody, new TypeToken<List<Country>>() {
		}.getType());

		// From description: "If there is no land crossing, the endpoint returns HTTP 400." This means self country request is 400 too.
		Map<String, Set<String>> countryMap = countryList.stream() //
				.filter(x -> !x.getBorders().isEmpty()) // filter out the countries without borders
				.collect(Collectors.toMap(Country::getCca3, Country::getBorders));

		return new CountryCache(countryMap);
	}

	public static class CountryCache {

		private Map<String, Set<String>> countryMap;

		public CountryCache(Map<String, Set<String>> countryMap) {
			this.countryMap = countryMap;
		}

		public Map<String, Set<String>> getCountryMap() {
			return countryMap;
		}
	}
}
