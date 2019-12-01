package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ActorRepositoryTestcontainerTest {

    @Container
    private PostgreSQLContainer container = new PostgreSQLContainer();

    private ActorRepository repositoryUnderTest;
    private DataSource ds;

    @BeforeEach
    void setup(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());

        ds = new HikariDataSource(hikariConfig);
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();

        repositoryUnderTest = new ActorRepository(ds);

    }


    @Test
    void findAllActor(){
        List<Actor> allActor = repositoryUnderTest.findAllActor();

        assertThat(allActor).hasSize(4);
    }

    @Test
    void saveActor(){
        repositoryUnderTest.save(new Actor("Kenny", "Baker", "R2-D2"));

        Integer result = new JdbcTemplate(ds).queryForObject("Select count(*) from actor where last_name = 'Baker'", Integer.class);
        assertThat(result).isEqualTo(1);
    }

}
