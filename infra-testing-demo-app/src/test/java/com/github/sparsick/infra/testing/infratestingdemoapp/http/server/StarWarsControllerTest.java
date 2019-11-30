package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StarWarsControllerTest {

    @Test
    void findAllMovies(){
        StarWarsController controllerUnderTest = new StarWarsController();
        List<StarWarsMovie> movies = controllerUnderTest.findAllMovies();

        assertThat(movies)
                .hasSize(2)
                .extracting("director")
                .containsExactlyInAnyOrder( "George Lucas","J. J. Abrams" );
    }
}