package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;


import groovy.text.SimpleTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.verify.VerificationTimes;
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
public class StarWarsClientMockserverTest {

    private static String starship1TestDataTemplate;
    private static String starship2TestDataTemplate;

    private MockServerClient mockServerClient;
    private StarWarsClient clientUnderTest;
    private String starshipTestData;
    private String starshipTestData2;

    public StarWarsClientMockserverTest(MockServerClient mockServerClient) {
        this.mockServerClient = mockServerClient;
    }

    @BeforeAll
    static void testDataSetup() throws IOException {
        try (InputStream inputStream = new ClassPathResource("starwars-testdata/starship1.json").getInputStream()) {
            starship1TestDataTemplate = IOUtils.toString(inputStream, Charset.defaultCharset());
        }

        try (InputStream inputStream = new ClassPathResource("starwars-testdata/starship2.json").getInputStream()) {
            starship2TestDataTemplate = IOUtils.toString(inputStream, Charset.defaultCharset());
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        clientUnderTest = new StarWarsClient("http://localhost:" + mockServerClient.remoteAddress().getPort());
        Map binding = new HashMap();
        binding.put("baseUrl","localhost:" + mockServerClient.remoteAddress().getPort());
        starshipTestData = new SimpleTemplateEngine().createTemplate(starship1TestDataTemplate).make(binding).toString();
        starshipTestData2 = new SimpleTemplateEngine().createTemplate(starship2TestDataTemplate).make(binding).toString();
    }

    @AfterEach
    void cleanUp(){
        mockServerClient.reset();
    }


    @Test
    void findAllStarships() {
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(starshipTestData)
                );
       mockServerClient
               .when(request()
                       .withMethod("GET")
                       .withPath("/api/starships2")
               )
               .respond(response()
                       .withBody(starshipTestData2)
               );

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);
    }


    @Test
    void verifyFindAllStarshipsRequest(){
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(starshipTestData)
                );

        clientUnderTest.findAllStarships();

        mockServerClient
                .verify(request()
                                .withMethod("GET")
                                .withPath("/api/starships"),
                        VerificationTimes.once());
        mockServerClient
                .verify(request()
                                .withMethod("GET")
                                .withPath("/api/starships2"),
                        VerificationTimes.once());
    }

    @Test
    void findAllCharacter() throws IOException {
        String characterTestData;
        try (InputStream inputStream = new ClassPathResource("starwars-testdata/character.json").getInputStream()) {
            characterTestData = IOUtils.toString(inputStream, Charset.defaultCharset());
        }
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/people")
                )
                .respond(response()
                        .withBody(characterTestData)
                );

        List<Character> characters = clientUnderTest.findAllCharacter();

        assertThat(characters).hasSize(10);
    }

    @Test
    void findAllCharacter_errorcase() throws Exception {
        String characterTestData = loadTestData("starwars-testdata/character-errorcase.json");
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/people")
                )
                .respond(response()
                        .withStatusCode(200)
                        .withBody(characterTestData)
                );

        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/people2")
                )
                .respond(response()
                        .withStatusCode(404)
                );

        List<Character> characters = clientUnderTest.findAllCharacter();

        assertThat(characters).hasSize(10);
    }

    @Test
    void findCharacterByName_verifyCalls() {
        clientUnderTest.findCharactersByName("Skywalker");

        mockServerClient
                .verify(request()
                                .withMethod("GET")
                                .withPath("/api/people/"),
                        VerificationTimes.once());
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