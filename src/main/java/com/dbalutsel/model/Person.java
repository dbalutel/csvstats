package com.dbalutsel.model;

import lombok.Data;
import uk.elementarysoftware.quickcsv.api.CSVRecordWithHeader;

import java.math.BigDecimal;

@Data
public class Person implements Comparable<Person> {

    private final String personId;

    private final String canton;

    private final BigDecimal income;

    public Person(CSVRecordWithHeader<PersonCsvFields> r) {
        this.personId = r.getField(PersonCsvFields.PersonId).asString();
        this.canton = r.getField(PersonCsvFields.Canton).asString();
        this.income = new BigDecimal(r.getField(PersonCsvFields.Income).asString());
    }

    @Override
    public int compareTo(Person o) {
        return income.compareTo(o.income);
    }

    public enum PersonCsvFields {
        PersonId,
        Canton,
        Income
    }
}
