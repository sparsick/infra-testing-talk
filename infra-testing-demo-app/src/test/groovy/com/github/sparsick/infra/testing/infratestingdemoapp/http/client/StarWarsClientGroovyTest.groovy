package com.github.sparsick.infra.testing.infratestingdemoapp.http.client

import groovy.text.SimpleTemplateEngine
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockserver.client.MockServerClient
import org.mockserver.junit.MockServerRule
import org.mockserver.verify.VerificationTimes

import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

class StarWarsClientGroovyTest {

    private static String starship1TestDataTemplate;
    private static String starship2TestDataTemplate;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    private MockServerClient mockServerClient = mockServerRule.getClient();
    private StarWarsClient clientUnderTest = new StarWarsClient("http","localhost", mockServerRule.getPort());
    private String testData;
    private String testData2;


    @BeforeClass
    static void "setup test data"()  {
        starship1TestDataTemplate = StarWarsClient.class.getResourceAsStream("/starwars-testdata/starship1.json").text
        starship2TestDataTemplate = StarWarsClient.class.getResourceAsStream("/starwars-testdata/starship2.json").text
    }

    @Before
    void "setup"() {
        Map binding = new HashMap()
        binding.put("baseUrl","localhost:" + mockServerRule.port)
        testData = new SimpleTemplateEngine().createTemplate(starship1TestDataTemplate).make(binding).toString()
        testData2 = new SimpleTemplateEngine().createTemplate(starship2TestDataTemplate).make(binding).toString()
    }


    @Test
    void "find all starships"() {
       mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(testData)
                )
       mockServerClient
               .when(request()
                       .withMethod("GET")
                       .withPath("/api/starships2")
               )
               .respond(response()
                       .withBody(testData2)
               )

        List<Starship> allStarships = clientUnderTest.findAllStarships()

        assert allStarships.size() == 11
    }


    @Test
    void "verify call for finding all starships"(){
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(testData)
                )
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships2")
                )
                .respond(response()
                        .withBody(testData2)
                )

        List<Starship> allStarships = clientUnderTest.findAllStarships()

        assert allStarships.size() == 11


        mockServerClient
                .verify(request()
                                .withMethod("GET")
                                .withPath("/api/starships"),
                        VerificationTimes.once())
        mockServerClient
                .verify(request()
                                .withMethod("GET")
                                .withPath("/api/starships2"),
                        VerificationTimes.once())
    }

}