package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ActorRepositoryEmbeddedDatabaseTest {

    private EmbeddedDatabase db;
    private ActorRepository repositoryUnderTest;

    @BeforeEach
    void setup(){
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V1_1_0_1__Create-Actor-Table.sql")
                .addScript("db/migration/V1_1_0_2__Insert-Actors.sql")
                .build();

        repositoryUnderTest = new ActorRepository(db);

    }

    @AfterEach
    void cleanUp(){
        db.shutdown();
    }


    @Test
    void findAllActor(){
        List<Actor> allActor = repositoryUnderTest.findAllActor();

        assertThat(allActor).hasSize(4);
    }

    @Test
    void saveActor(){
        repositoryUnderTest.save(new Actor("Kenny", "Baker", "R2-D2"));

        Integer result = new JdbcTemplate(db).queryForObject("Select count(*) from actor where last_name = 'Baker'", Integer.class);
        assertThat(result).isEqualTo(1);
    }

}