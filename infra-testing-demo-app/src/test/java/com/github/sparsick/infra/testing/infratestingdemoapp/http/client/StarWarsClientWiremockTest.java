package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;


import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import groovy.text.SimpleTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class StarWarsClientWiremockTest {

    @ClassRule
    public static WireMockClassRule serviceMock = new WireMockClassRule(options().dynamicPort());

    private StarWarsClient clientUnderTest = new StarWarsClient("http://localhost:" + serviceMock.port());

    @Test
    public void findAllStarships() throws Exception {
        String testData = loadTestData("starwars-testdata/starship1.json");
        String testData2 = loadTestData("starwars-testdata/starship2.json");

        serviceMock.stubFor(get(urlEqualTo("/api/starships"))
                                .willReturn(aResponse().withBody(testData)));
        serviceMock.stubFor(get(urlEqualTo("/api/starships2"))
                                .willReturn(aResponse().withBody(testData2)));

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);
    }


    @Test
    public void verifyFindAllStarshipsRequest() throws Exception {
        String testData = loadTestData("starwars-testdata/starship1.json");

        serviceMock.stubFor(get(urlEqualTo("/api/starships"))
                .willReturn(aResponse().withBody(testData)));

        clientUnderTest.findAllStarships();

        serviceMock.verify(1, getRequestedFor(urlEqualTo("/api/starships")));
        serviceMock.verify(1, getRequestedFor(urlEqualTo("/api/starships2")));
    }

    @Test
    public void findCharacterByName() throws Exception {
        String characterTestData = loadTestData("starwars-testdata/find-character.json");
        String searchByName = "Skywalker";
        serviceMock.stubFor(get(urlPathEqualTo("/api/people/"))
                                    .withQueryParam("search",equalTo(searchByName))
                .willReturn(aResponse()
                                    .withBody(characterTestData)));

        List<Character> characters = clientUnderTest.findCharactersByName(searchByName);

        assertThat(characters).hasSize(3);
    }


    @Test
    public void findCharacterByName_verifyCalls() {
        String searchByName = "Skywalker";
        clientUnderTest.findCharactersByName(searchByName);

        serviceMock.verify(1, getRequestedFor(urlPathEqualTo("/api/people/"))
                                                        .withQueryParam("search" , equalTo(searchByName)));
    }

    private String loadTestData(String testdataPath) throws IOException, ClassNotFoundException {
        String testData;
        try (InputStream inputStream = new ClassPathResource(testdataPath).getInputStream()) {
            testData = IOUtils.toString(inputStream, Charset.defaultCharset());
        }

        Map binding = new HashMap();
        binding.put("baseUrl","localhost:" + serviceMock.port());
        testData = new SimpleTemplateEngine().createTemplate(testData).make(binding).toString();
        return testData;
    }

}