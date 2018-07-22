package com.github.sparsick.infra.testing.infratestingdemoapp.http.server;

import java.util.Objects;

public class StarWarsMovie {

    private String title;
    private String director;

    public StarWarsMovie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarWarsMovie that = (StarWarsMovie) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(director, that.director);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, director);
    }

    @Override
    public String toString() {
        return "StarWarsMovie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
