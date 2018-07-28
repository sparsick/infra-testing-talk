package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarWarsClient {


    private final RestTemplate restTemplate;
    private final String baseUrl;

    public StarWarsClient(String protocol, String hostName, int port) {
        this.restTemplate = new RestTemplateBuilder().build();
        try {
            this.baseUrl = new URL(protocol, hostName, port, "").toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public StarWarsClient(String protocol, String hostName) {
        this.restTemplate = new RestTemplateBuilder().build();
        try {
            this.baseUrl = new URL(protocol, hostName,"").toString();
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
}
