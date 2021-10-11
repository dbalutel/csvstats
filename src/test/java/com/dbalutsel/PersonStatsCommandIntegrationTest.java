package com.dbalutsel;

import com.dbalutsel.command.PersonStatsCommand;
import com.dbalutsel.model.Person;
import com.dbalutsel.model.Stats;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonStatsCommandIntegrationTest {

    @Test
    public void testWithCommandLineOption() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[]{"-f", "src/test/resources/mockdata.csv"};
            Stats<Person, String> result = PicocliRunner.call(PersonStatsCommand.class, ctx, args);

            assertThat(result.getMax().getIncome()).isEqualTo(BigDecimal.valueOf(999999L));
            assertThat(result.getMax().getCanton()).isEqualTo("LU");
            assertThat(result.getMax().getPersonId()).isEqualTo("016");

            assertThat(result.getMin().getIncome()).isEqualTo(BigDecimal.valueOf(10000L));
            assertThat(result.getMin().getCanton()).isEqualTo("ZH");
            assertThat(result.getMin().getPersonId()).isEqualTo("001");

            Map<String, BigDecimal> expectedGroupByResult = Map.of("BE", new BigDecimal(60100L),
                    "SZ", new BigDecimal(65125L),
                    "LU", new BigDecimal(302580L),
                    "ZH", new BigDecimal(156025L));
            assertThat(result.getGroupByAverage()).containsExactlyInAnyOrderEntriesOf(expectedGroupByResult);
        }
    }
}
