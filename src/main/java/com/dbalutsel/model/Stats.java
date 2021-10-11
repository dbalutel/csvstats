package com.dbalutsel.model;

import com.dbalutsel.collector.StatsAccumulator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class Stats<T extends Comparable<T>, K> {
    private T max;
    private T min;
    private Map<K, BigDecimal> groupByAverage;

    public Stats(StatsAccumulator<T, K> statsAccumulator) {
        this.max = statsAccumulator.getMax();
        this.min = statsAccumulator.getMin();
        this.groupByAverage = statsAccumulator.getGroupByAverage();
    }
}
