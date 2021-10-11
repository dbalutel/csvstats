package com.dbalutsel.command;

import com.dbalutsel.collector.StatsCollector;
import com.dbalutsel.model.Person;
import com.dbalutsel.model.Stats;
import com.dbalutsel.processor.CsvProcessor;
import jakarta.inject.Inject;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(name = "person-stats", description = "Produces statistics from given CSV document.",
        mixinStandardHelpOptions = true)
@Log
@Setter
public class PersonStatsCommand implements Callable<Stats<Person, String>> {


    @Option(names = {"-f", "--file"}, description = "Path to CSV file.")
    File file;

    @Inject
    CsvProcessor<Person.PersonCsvFields, Person> csvProcessor;

    @SneakyThrows
    public Stats<Person, String> call() {
        Stream<Person> personStream = csvProcessor.streamCsv(file, Person::new, Person.PersonCsvFields.class);

        Stats<Person, String> statsPerCanton = personStream.collect(new StatsCollector<>(Person::getCanton, Person::getIncome));

        log.info(statsPerCanton.toString());

        return statsPerCanton;
    }
}
