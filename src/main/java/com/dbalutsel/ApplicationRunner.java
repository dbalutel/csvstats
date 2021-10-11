package com.dbalutsel;

import com.dbalutsel.command.PersonStatsCommand;
import io.micronaut.configuration.picocli.PicocliRunner;

public class ApplicationRunner {
    public static void main(String[] args) {
        // to run from IDE pass "-f", "[ABSOLUTE_PATH]:/src/test/resources/mockdata.csv"
        PicocliRunner.call(PersonStatsCommand.class, args);
    }
}
