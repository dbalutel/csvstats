package com.dbalutsel.collector;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatsAccumulator<T extends Comparable<T>, K> {
    private Map<K, List<BigDecimal>> groupByMap = new HashMap<>();

    @Getter
    private T min;

    @Getter
    private T max;

    public void accumulateValue(K key, BigDecimal value) {
        List<BigDecimal> valuesGroupedBy = groupByMap.get(key);
        if (valuesGroupedBy == null) {
            valuesGroupedBy = new ArrayList<>();
            valuesGroupedBy.add(value);
            groupByMap.put(key, valuesGroupedBy);
        } else {
            valuesGroupedBy.add(value);
        }
    }

    public void accumulateMinMax(T object) {
        if (max == null || object.compareTo(max) > 0) {
            max = object;
        }
        if (min == null || object.compareTo(min) < 0) {
            min = object;
        }
    }

    public StatsAccumulator<T, K> combine(StatsAccumulator<T, K> accumToMerge) {
        if (accumToMerge.isEmpty()) {
            return this;
        }

        if (accumToMerge.min.compareTo(min) < 0) {
            min = accumToMerge.min;
        }
        if (accumToMerge.max.compareTo(max) > 0) {
            max = accumToMerge.max;
        }

        groupByMap = Stream.concat(groupByMap.entrySet().stream(), accumToMerge.groupByMap.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> {
                            value1.addAll(value2);
                            return value1;
                        }));
        return this;
    }

    public Map<K, BigDecimal> getGroupByAverage() {
        return groupByMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(entry.getValue().size()), RoundingMode.HALF_UP)));
    }

    private boolean isEmpty() {
        return Objects.isNull(min) && Objects.isNull(max) && groupByMap.isEmpty();
    }
}
