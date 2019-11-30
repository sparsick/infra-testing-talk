package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StarWarsController.class)
public class StarWarsControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllMovies() {
        given().mockMvc(mockMvc).
                when().get("/starwars/movies").
                then().statusCode(200)
                .body("title", hasItems("A New Hope", "The Force Awakens"))
                .body("findAll{ it.director == 'George Lucas'}.title", hasItem("A New Hope"));
    }

    @Test
    void findAllMovies_plainMockMvc() throws Exception {
        mockMvc.perform(get("/starwars/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..title").value(hasItems("A New Hope", "The Force Awakens")))
                .andExpect(jsonPath("$[?(@.director == 'George Lucas')].title").value(hasItem("A New Hope")));

    }

    @Test
    void findAllActors_plainMockMvc() throws Exception {
        mockMvc.perform(get("/starwars/actors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..role").value(hasItems("Luke Skywalker", "Princess Leia", "Han Solo")))
                .andExpect(jsonPath("$..actors[?(@.lastName == 'Fisher')].role").value("Princess Leia"));
    }

    @Test
    void findAllActors() {
        given().mockMvc(mockMvc).
                when().get("/starwars/actors").
                then().statusCode(200)
                .body("actors.role", hasItems("Luke Skywalker", "Princess Leia", "Han Solo"))
                .body("actors.findAll { actor -> actor.lastName == 'Fisher' }.role", hasItem("Princess Leia"));
    }
}