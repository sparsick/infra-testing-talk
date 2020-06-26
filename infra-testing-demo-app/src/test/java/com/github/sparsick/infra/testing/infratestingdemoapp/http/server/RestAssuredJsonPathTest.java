package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

public class RestAssuredJsonPathTest {

    @Test
    void demoSimpleElement() throws Exception {
        String json = loadJson("starwars-testdata/one-starship-object.json");

        String starShipName = from(json).get("starship.name");

        assertThat(starShipName).isEqualTo("Slave 1");

    }

    @Test
    void demoArray() throws Exception {
        String json = loadJson("starwars-testdata/one-starship-object.json");

        List<String> allFilmTitles = from(json).get("starship.films.title");

        assertThat(allFilmTitles).hasSize(2);

    }

    private String loadJson(String jsonPath) throws IOException {
        try (InputStream inputStream = new ClassPathResource(jsonPath).getInputStream()) {
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        }
    }

}
