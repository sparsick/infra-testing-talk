package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;


import groovy.text.SimpleTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
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

public class StarWarsClientTest {

    private static String starship1TestDataTemplate;
    private static String starship2TestDataTemplate;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, false);

    private MockServerClient mockServerClient;
    private StarWarsClient clientUnderTest = new StarWarsClient("http","localhost", mockServerRule.getPort());
    private String testData;
    private String testData2;


    @BeforeClass
    public static void testDataSetup() throws IOException {
        try (InputStream inputStream = new ClassPathResource("starwars-testdata/starship1.json").getInputStream()) {
            starship1TestDataTemplate = IOUtils.toString(inputStream, Charset.defaultCharset());
        }

        try (InputStream inputStream = new ClassPathResource("starwars-testdata/starship2.json").getInputStream()) {
            starship2TestDataTemplate = IOUtils.toString(inputStream, Charset.defaultCharset());
        }
    }

    @Before
    public void setUp() throws Exception {
        Map binding = new HashMap();
        binding.put("baseUrl","localhost:" + mockServerRule.getPort());
        testData = new SimpleTemplateEngine().createTemplate(starship1TestDataTemplate).make(binding).toString();
        testData2 = new SimpleTemplateEngine().createTemplate(starship2TestDataTemplate).make(binding).toString();
    }


    @Test
    public void findAllStarships() {
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(testData)
                );
       mockServerClient
               .when(request()
                       .withMethod("GET")
                       .withPath("/api/starships2")
               )
               .respond(response()
                       .withBody(testData2)
               );

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);
    }


    @Test
    public void verifyFindAllStarshipsRequest(){
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(testData)
                );
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships2")
                )
                .respond(response()
                        .withBody(testData2)
                );

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);

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

}