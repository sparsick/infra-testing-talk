package com.github.sparsick.infra.testing.infratestingdemoapp.database;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class GenericContainerSample {

    GenericContainer alpine =
            new GenericContainer("alpine:3.2")
                    .withExposedPorts(80)
                    .withEnv("MAGIC_NUMBER", "42")
                    .withCommand("/bin/sh", "-c",
                            "while true; do echo \"$MAGIC_NUMBER\" | nc -l -p 80; done")
                    .waitingFor(Wait.forLogMessage("42", 1));



}
