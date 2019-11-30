package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarWarsClient {


    private final RestTemplate restTemplate;
    private final String baseUrl;

    public StarWarsClient(String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().build();
        try {
            this.baseUrl = new URL(baseUrl).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Starship> findAllStarships() {
        List<Starship> starships = new ArrayList<>();
        String nextPageUrl = baseUrl + "/api/starships";
        do {
            String forObject = restTemplate.getForObject(nextPageUrl, String.class);
            Map<String, Object> jsonMap = new GsonJsonParser().parseMap(forObject);
            nextPageUrl = (String) jsonMap.get("next");
            for (Map result : (List<Map>) jsonMap.get("results")) {
                starships.add(Starship.from(result));
            }
        }
        while (nextPageUrl != null);
        return starships;
    }

    public List<Character> findAllCharacter() {
        List<Character> characters = new ArrayList<>();
        String nextPageUrl = baseUrl + "/api/people";
        do {
            try {
                String forObject = restTemplate.getForObject(nextPageUrl, String.class);
                Map<String, Object> jsonMap = new GsonJsonParser().parseMap(forObject);
                nextPageUrl = (String) jsonMap.get("next");
                for (Map result : (List<Map>) jsonMap.get("results")) {
                    characters.add(Character.from(result));
                }
            } catch (HttpClientErrorException.NotFound e) {
                // log error and finished grepping
                nextPageUrl = null;
            }

        }
        while (nextPageUrl != null);
        return characters;
    }

    public List<Character> findCharactersByName(String name) {
        List<Character> characters = new ArrayList<>();
        String nextPageUrl = baseUrl + "/api/people/?search=" + name;
        do {
            try {
                String forObject = restTemplate.getForObject(nextPageUrl, String.class);
                Map<String, Object> jsonMap = new GsonJsonParser().parseMap(forObject);
                nextPageUrl = (String) jsonMap.get("next");
                for (Map result : (List<Map>) jsonMap.get("results")) {
                    characters.add(Character.from(result));
                }
            } catch (HttpClientErrorException.NotFound e) {
                // log error and finished grepping
                nextPageUrl = null;
            }

        }
        while (nextPageUrl != null);
        return characters;
    }
}
