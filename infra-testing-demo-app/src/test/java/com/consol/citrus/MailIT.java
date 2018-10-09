package com.consol.citrus;

import com.consol.citrus.annotations.CitrusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.Console;


/**
 *
 *
 * @author Sandra Parsick
 * @since 2018-10-04
 */
@ExtendWith(com.consol.citrus.junit.jupiter.CitrusBaseExtension.class)
public class MailIT {
    @CitrusTest
    @Test
    public void mail() {
        Console console = System.console();
        console.printf("TODO: Code the test MailIT");

    }
}
