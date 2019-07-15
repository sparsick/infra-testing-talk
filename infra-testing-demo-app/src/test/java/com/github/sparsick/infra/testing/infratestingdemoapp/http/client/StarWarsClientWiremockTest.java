package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;


import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import groovy.text.SimpleTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
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
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.Options.DYNAMIC_PORT;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

public class StarWarsClientWiremockTest {

    private static String starship1TestDataTemplate;
    private static String starship2TestDataTemplate;

    @ClassRule
    public static WireMockClassRule serviceMock = new WireMockClassRule(options().port(DYNAMIC_PORT));

    private StarWarsClient clientUnderTest = new StarWarsClient("http", "localhost", serviceMock.port());
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
        binding.put("baseUrl","localhost:" + serviceMock.port());
        testData = new SimpleTemplateEngine().createTemplate(starship1TestDataTemplate).make(binding).toString();
        testData2 = new SimpleTemplateEngine().createTemplate(starship2TestDataTemplate).make(binding).toString();
    }


    @Test
    public void findAllStarships() {
        serviceMock.stubFor(get(urlEqualTo("/api/starships"))
                                .willReturn(aResponse().withBody(testData)));
        serviceMock.stubFor(get(urlEqualTo("/api/starships2"))
                                .willReturn(aResponse().withBody(testData2)));

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);
    }


    @Test
    public void verifyFindAllStarshipsRequest(){
        serviceMock.stubFor(get(urlEqualTo("/api/starships"))
                .willReturn(aResponse().withBody(testData)));
        serviceMock.stubFor(get(urlEqualTo("/api/starships2"))
                .willReturn(aResponse().withBody(testData2)));

        List<Starship> allStarships = clientUnderTest.findAllStarships();

        assertThat(allStarships).hasSize(11);

        serviceMock.verify(1, getRequestedFor(urlEqualTo("/api/starships")));
        serviceMock.verify(1, getRequestedFor(urlEqualTo("/api/starships2")));
    }

}