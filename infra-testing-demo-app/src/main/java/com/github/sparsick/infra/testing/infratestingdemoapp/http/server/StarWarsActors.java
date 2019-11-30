package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarWarsActors {

    private List<StarWarsActor> actors = new ArrayList<>();

    public void add(StarWarsActor actor) {
        actors.add(actor);
    }

    public List<StarWarsActor> list() {
        return Collections.unmodifiableList(actors);
    }

    public List<StarWarsActor> getActors() {
        return Collections.unmodifiableList(actors);
    }
}
