package com.github.sparsick.infra.testing.infratestingdemoapp.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class PersonRepositorySpockTest extends Specification {

    @Shared
    private PostgreSQLContainer postgres = new PostgreSQLContainer();

    private PersonRepository repositoryUnderTest;


    def setup() {
        HikariConfig hikariConfig = new HikariConfig()
        hikariConfig.jdbcUrl = postgres.jdbcUrl
        hikariConfig.username = postgres.username
        hikariConfig.password = postgres.password

        HikariDataSource ds = new HikariDataSource(hikariConfig)
        Flyway flyway = Flyway.configure().dataSource(ds).load()
        flyway.migrate()

        repositoryUnderTest = new PersonRepository(ds)
    }


    def "save and find all person in the database"() {
        given:
        def person = new Person()
        person.firstName = "firstName"
        person.lastName = "lastName"
        person.jobTitle = "jobTitle"

        when:
        repositoryUnderTest.save(person);
        List<Person> persons = repositoryUnderTest.findAllPersons()

        then:
        persons.size == 1
        persons.contains(person)
    }
}