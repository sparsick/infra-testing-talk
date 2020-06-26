package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import io.restassured.path.xml.element.Node;
import io.restassured.path.xml.element.NodeChildren;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import static io.restassured.path.xml.XmlPath.from;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class RestAssuredXmlPathTest {

    @Test
    void demoSimpleElement() throws Exception {
        String xml = loadXml("starwars-testdata/one-starship-object.xml");

        String starShipModel = from(xml).get("starship.model");

        assertThat(starShipModel).isEqualTo("Firespray-31-class patrol and attack");
    }

    @Test
    void demoArray() throws Exception {
        String json = loadXml("starwars-testdata/one-starship-object.xml");

       List<String> allFilmDirector = ((NodeChildren)from(json).get("starship.films.film.director"))
                                            .list()
                                            .stream()
                                            .map(Node::value)
                                            .collect(toList());


        assertThat(allFilmDirector).hasSize(2).contains("George Lucas", "Irvin Kershner");
    }

    private String loadXml(String jsonPath) throws IOException {
        try (InputStream inputStream = new ClassPathResource(jsonPath).getInputStream()) {
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        }
    }

}
