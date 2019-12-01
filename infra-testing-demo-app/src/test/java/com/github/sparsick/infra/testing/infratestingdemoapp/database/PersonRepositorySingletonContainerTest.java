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

public class PersonRepositorySingletonContainerTest extends AbstractPostgresqlBaseTest {

    private PersonRepository repositoryUnderTest;
    private DataSource ds;

    @BeforeEach
    void setup(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(POSTGRE_SQL_CONTAINER.getJdbcUrl());
        hikariConfig.setUsername(POSTGRE_SQL_CONTAINER.getUsername());
        hikariConfig.setPassword(POSTGRE_SQL_CONTAINER.getPassword());

        ds = new HikariDataSource(hikariConfig);
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();

        repositoryUnderTest = new PersonRepository(ds);
    }

    @AfterEach
    void cleanUp(){
        new JdbcTemplate(ds).update("DROP SCHEMA public CASCADE;\n" +
                "CREATE SCHEMA public;\n" +
                "GRANT ALL ON SCHEMA public TO public;\n" +
                "COMMENT ON SCHEMA public IS 'standard public schema';");
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
