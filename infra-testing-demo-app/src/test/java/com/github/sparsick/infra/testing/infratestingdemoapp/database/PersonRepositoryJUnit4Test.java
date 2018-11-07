package com.github.sparsick.infra.testing.infratestingdemoapp.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonRepositoryJUnit4Test {

    @Rule
    public PostgreSQLContainer postgres = new PostgreSQLContainer();

    private PersonRepository repositoryUnderTest;

    @Before
    public void setup(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(postgres.getJdbcUrl());
        hikariConfig.setUsername(postgres.getUsername());
        hikariConfig.setPassword(postgres.getPassword());

        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Flyway flyway = new Flyway();
        flyway.setDataSource(ds);
        flyway.migrate();

        repositoryUnderTest = new PersonRepository(ds);
    }

    @Test
    public void saveAndFindAllPerson() {
       Person person = new Person();
       person.setFirstName("firstName");
       person.setLastName("lastName");
       person.setJobTitle("jobTitle");

       repositoryUnderTest.save(person);

        List<Person> persons = repositoryUnderTest.findAllPersons();
        assertThat(persons).hasSize(1).contains(person);
    }
}