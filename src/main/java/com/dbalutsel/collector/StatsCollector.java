package com.dbalutsel.collector;

import com.dbalutsel.model.Stats;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@AllArgsConstructor
public class StatsCollector<T extends Comparable<T>, K> implements Collector<T, StatsAccumulator<T, K>, Stats<T, K>> {

    private Function<T, K> keyGroupByExtractor;
    private Function<T, BigDecimal> valueGroupByExtractor;

    @Override
    public Supplier<StatsAccumulator<T, K>> supplier() {
        return StatsAccumulator::new;
    }

    @Override
    public BiConsumer<StatsAccumulator<T, K>, T> accumulator() {
        return (accum, object) -> {
            accum.accumulateValue(keyGroupByExtractor.apply(object), valueGroupByExtractor.apply(object));
            accum.accumulateMinMax(object);
        };
    }

    @Override
    public BinaryOperator<StatsAccumulator<T, K>> combiner() {
        return StatsAccumulator::combine;
    }

    @Override
    public Function<StatsAccumulator<T, K>, Stats<T, K>> finisher() {
        return Stats::new;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.singleton(Characteristics.UNORDERED);
    }


}
