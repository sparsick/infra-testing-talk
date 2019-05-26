package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StarWarsMovieController.class)
public class StarWarsMovieControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllMovies(){
        given().mockMvc(mockMvc).
        when().get("/starwars/movies").
        then().statusCode(200)
                .body("title",  hasItems("A New Hope", "The Force Awakens"))
                .body("director", hasItems("George Lucas", "J. J. Abrams"));
    }

    @Test
    public void findAllMovies_plainMockMvc() throws Exception {
        mockMvc.perform(get("/starwars/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].title").value("A New Hope"))
                .andExpect(jsonPath("[0].director").value("George Lucas"))
                .andExpect(jsonPath("[1].title").value("The Force Awakens"))
                .andExpect(jsonPath("[1].director").value("J. J. Abrams"));

    }

}