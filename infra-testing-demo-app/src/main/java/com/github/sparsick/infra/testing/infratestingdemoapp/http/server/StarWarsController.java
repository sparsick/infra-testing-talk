package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("starwars")
public class StarWarsController {

    @RequestMapping(value="movies", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<StarWarsMovie> findAllMovies(){
        return List.of(new StarWarsMovie("A New Hope", "George Lucas"),
                new StarWarsMovie("The Force Awakens", "J. J. Abrams"));
    }


    @RequestMapping(value="actors", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody StarWarsActors findAllActors(){
        StarWarsActors actors = new StarWarsActors();
        actors.add(new StarWarsActor("Mark", "Hamill", "Luke Skywalker"));
        actors.add(new StarWarsActor("Carrie", "Fisher", "Princess Leia"));
        actors.add(new StarWarsActor("Harrison", "Ford", "Han Solo"));
        actors.add(new StarWarsActor("Alden", "Ehrenreich", "Han Solo"));

        return actors;
    }
}
