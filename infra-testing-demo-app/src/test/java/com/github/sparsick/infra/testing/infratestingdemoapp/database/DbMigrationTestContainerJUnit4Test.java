package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.flywaydb.core.Flyway;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

public class DbMigrationTestContainerJUnit4Test {
    
    @Rule
    public MySQLContainer mysqlDb = new MySQLContainer("mysql:8.0.28");
    
    @Test
    public void testDbMigrationFromTheScratch(){
        Flyway flyway = Flyway.configure().dataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword()).load();


        flyway.migrate();
    }
    
}