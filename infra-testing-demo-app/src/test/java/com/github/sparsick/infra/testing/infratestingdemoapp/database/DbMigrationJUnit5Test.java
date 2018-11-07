package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class DbMigrationJUnit5Test {
    
    @Container
    private MySQLContainer mysqlDb = new MySQLContainer();
    
    @Test
    void testDbMigrationFromTheScratch(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(mysqlDb.getJdbcUrl(), mysqlDb.getUsername(), mysqlDb.getPassword());
        
        flyway.migrate();
    }
    
}