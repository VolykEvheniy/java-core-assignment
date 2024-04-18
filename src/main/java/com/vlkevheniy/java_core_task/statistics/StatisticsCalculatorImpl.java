package com.vlkevheniy.java_core_task.statistics;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link StatisticsCalculator} for accumulating and tracking statistics.
 */
public class StatisticsCalculatorImpl implements StatisticsCalculator {

    private final ConcurrentHashMap<String, Integer> brandStatistics = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> yearStatistics = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> colorStatistics = new ConcurrentHashMap<>();


    @Override
    public void incrementBrandCount(String brand) {
        brandStatistics.merge(brand, 1, Integer::sum);
    }

    @Override
    public void incrementYearCount(String year) {
        yearStatistics.merge(year, 1, Integer::sum);
    }

    @Override
    public void incrementColorCount(String color) {
        String[] colors = color.split(",");
        for (String col: colors) {
            colorStatistics.merge(col.trim(), 1, Integer::sum);
        }
    }

    public ConcurrentHashMap<String, Integer> getBrandStatistics() {
        return brandStatistics;
    }

    @Override
    public ConcurrentHashMap<String, Integer> getYearStatistics() {
        return yearStatistics;
    }

    public ConcurrentHashMap<String, Integer> getColorStatistics() {
        return colorStatistics;
    }
}
