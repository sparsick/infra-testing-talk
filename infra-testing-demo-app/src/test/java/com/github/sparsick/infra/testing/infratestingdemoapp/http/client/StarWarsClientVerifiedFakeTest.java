package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import groovy.text.SimpleTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@Tag("verifyFake")
public class StarWarsClientVerifiedFakeTest {

    private static Logger LOGGER = LoggerFactory.getLogger(StarWarsClientVerifiedFakeTest.class);

    private StarWarsClient clientUnderTest;
    private MockServerClient mockServerClient;

    public StarWarsClientVerifiedFakeTest(MockServerClient mockServerClient) {
        this.mockServerClient = mockServerClient;
    }

    @BeforeEach
    void setUp()  {
        String baseUrl = "http://localhost:"+ mockServerClient.remoteAddress().getPort();

        if(System.getProperty("swapi.base.url") != null) {
            baseUrl = System.getProperty("swapi.base.url");
        }

        LOGGER.info("Used BaseURL: " + baseUrl);

        clientUnderTest = new StarWarsClient(baseUrl);
    }

    @Test
    void findCharacterByName() throws Exception {
        String characterTestData = loadTestData("starwars-testdata/find-character.json");
        String searchByName = "Skywalker";
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/people/")
                        .withQueryStringParameter("search", searchByName)
                )
                .respond(response()
                        .withStatusCode(200)
                        .withBody(characterTestData)
                );



        List<Character> characters = clientUnderTest.findCharactersByName(searchByName);

        assertThat(characters).hasSize(3);
    }

    private String loadTestData(String testdataPath) throws IOException, ClassNotFoundException {
        String characterTestData;
        try (InputStream inputStream = new ClassPathResource(testdataPath).getInputStream()) {
            characterTestData = IOUtils.toString(inputStream, Charset.defaultCharset());
        }

        Map binding = new HashMap();
        binding.put("baseUrl","localhost:" + mockServerClient.remoteAddress().getPort());
        characterTestData = new SimpleTemplateEngine().createTemplate(characterTestData).make(binding).toString();
        return characterTestData;
    }

}
