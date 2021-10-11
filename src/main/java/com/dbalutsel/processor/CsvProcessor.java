package com.dbalutsel.processor;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import uk.elementarysoftware.quickcsv.api.CSVParser;
import uk.elementarysoftware.quickcsv.api.CSVParserBuilder;
import uk.elementarysoftware.quickcsv.api.CSVRecordWithHeader;

import java.io.File;
import java.util.function.Function;
import java.util.stream.Stream;

@Singleton
@Requires
public class CsvProcessor<F extends Enum<F>, T> {

    @SneakyThrows
    public Stream<T> streamCsv(File file, Function<CSVRecordWithHeader<F>, T> mapper, Class<F> fields) {

        CSVParser<T> parser = CSVParserBuilder.aParser(mapper, fields)
                .forRfc4180()
                .build();

        return parser.parse(file);
    }
}
