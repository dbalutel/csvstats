package com.dbalutsel.command;

import com.dbalutsel.model.Person;
import com.dbalutsel.processor.CsvProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.function.Function;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonStatsCommandTest {

    @Mock
    private File file;

    @Mock
    private CsvProcessor<Person.PersonCsvFields, Person> csvProcessor;

    private PersonStatsCommand personStatsCommand;

    @BeforeEach
    void setup() {
        personStatsCommand = new PersonStatsCommand();
        personStatsCommand.setFile(file);
        personStatsCommand.setCsvProcessor(csvProcessor);
    }

    @Test
    void testPersonStatCommand() {

        personStatsCommand.call();
        verify(csvProcessor).streamCsv(eq(file), any(Function.class), eq(Person.PersonCsvFields.class));

    }
}
