package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractPostgresqlBaseTest {

    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:9.6.12");
        POSTGRE_SQL_CONTAINER.start();
    }

}
