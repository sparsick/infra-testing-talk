package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonRepositoryJdbcUrlTestContainerTest {
    private PersonRepository repositoryUnderTest;
    private Flyway flyway;

    @BeforeEach
    void setup(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:tc:postgresql:14.1:///persondb");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("");

        DataSource ds = new HikariDataSource(hikariConfig);
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();

        repositoryUnderTest = new PersonRepository(ds);

    }

    @AfterEach
    void cleanUp(){
        flyway.clean();
    }


    @Test
    void saveAndFindAllPerson() {
        Person person = new Person();
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setJobTitle("jobTitle");

        repositoryUnderTest.save(person);

        List<Person> persons = repositoryUnderTest.findAllPersons();
        assertThat(persons).hasSize(1).contains(person);
    }

}


