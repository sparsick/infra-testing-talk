package com.github.sparsick.infra.testing.infratestingdemoapp.database;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonRepository {

    private JdbcTemplate jdbcTemplate;


    public PersonRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Person> findAllPersons() {
        return jdbcTemplate.query("Select * from person;", new RowMapper<Person>() {

            @Override
            public Person mapRow(ResultSet resultSet, int arg1) throws SQLException {
                Person person = new Person();
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setJobTitle(resultSet.getString("job_title"));
                return person;
            }

        });

    }


    public void save(Person person) {
        jdbcTemplate.update("Insert into person (first_name,last_name,job_title) values(?,?,?)", person.getFirstName(),
                person.getLastName(), person.getJobTitle());
    }
}
