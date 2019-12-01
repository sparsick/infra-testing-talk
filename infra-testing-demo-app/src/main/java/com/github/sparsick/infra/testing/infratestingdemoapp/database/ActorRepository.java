package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ActorRepository {

    private JdbcTemplate jdbcTemplate;


    public ActorRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Actor> findAllActor() {
        return jdbcTemplate.query("Select * from actor;", new RowMapper<>() {

            @Override
            public Actor mapRow(ResultSet resultSet, int arg1) throws SQLException {
                Actor actor = new Actor(resultSet.getString("first_name"),
                                        resultSet.getString("last_name"),
                                        resultSet.getString("role"));
                return actor;
            }

        });

    }


    public void save(Actor actor) {
        jdbcTemplate.update("Insert into actor (first_name,last_name,role) values(?,?,?)", actor.getFirstName(),
                actor.getLastName(), actor.getRole());
    }
}
