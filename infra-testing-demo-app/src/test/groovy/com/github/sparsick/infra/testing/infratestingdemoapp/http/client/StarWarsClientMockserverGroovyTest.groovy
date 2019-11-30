package com.github.sparsick.infra.testing.infratestingdemoapp.http.client

import groovy.text.SimpleTemplateEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.verify.VerificationTimes

import static org.mockserver.model.HttpRequest.request
import static org.mockserver.model.HttpResponse.response

@ExtendWith(MockServerExtension.class)
class StarWarsClientMockserverGroovyTest {

    private MockServerClient mockServerClient
    private StarWarsClient clientUnderTest

    StarWarsClientMockserverGroovyTest(MockServerClient mockServerClient) {
        this.mockServerClient = mockServerClient
    }

    @BeforeEach
    void "setup"() {
        clientUnderTest = new StarWarsClient("http://localhost:" + mockServerClient.remoteAddress().port)
    }

    @Test
    void "find all starships"() {
        def testData = loadTestData("/starwars-testdata/starship1.json")
        def testData2 = loadTestData("/starwars-testdata/starship2.json")
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
    void "verify call for finding all starships"() {
        def testData = loadTestData("/starwars-testdata/starship1.json")
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/api/starships")
                )
                .respond(response()
                        .withBody(testData)
                )

        clientUnderTest.findAllStarships()


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

    String loadTestData(String jsonPath) {
        def testData = StarWarsClient.class.getResourceAsStream(jsonPath).text

        Map binding = ["baseUrl": "localhost:" + mockServerClient.remoteAddress().port]
        new SimpleTemplateEngine().createTemplate(testData).make(binding).toString()
    }
}