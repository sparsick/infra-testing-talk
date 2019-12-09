package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class ActorRepositoryGenericContainerTest {

    @Container
    private GenericContainer container = new GenericContainer("postgres:12.1")
            .withExposedPorts(5432)
            .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\s", 2));

    private ActorRepository repositoryUnderTest;
    private DataSource ds;
    private Flyway flyway;

    @BeforeEach
    void setup() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:" + container.getMappedPort(5432) + "/postgres");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("");

        ds = new HikariDataSource(hikariConfig);
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();

        repositoryUnderTest = new ActorRepository(ds);

    }

    @AfterEach
    void cleanUp() {
        flyway.clean();
    }


    @Test
    void findAllActor() {
        List<Actor> allActor = repositoryUnderTest.findAllActor();

        assertThat(allActor).hasSize(4);
    }

    @Test
    void saveActor() {
        repositoryUnderTest.save(new Actor("Kenny", "Baker", "R2-D2"));

        Integer result = new JdbcTemplate(ds).queryForObject("Select count(*) from actor where last_name = 'Baker'", Integer.class);
        assertThat(result).isEqualTo(1);
    }
}
