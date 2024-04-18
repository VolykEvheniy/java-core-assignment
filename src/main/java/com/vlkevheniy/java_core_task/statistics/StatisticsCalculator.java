package com.vlkevheniy.java_core_task.statistics;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Interface for calculating statistics based on car data.
 */
public interface StatisticsCalculator {

    /**
     * Increments the count of occurrences for a given brand.
     *
     * @param brand The brand name to increment the count for.
     */
    void incrementBrandCount(String brand);

    /**
     * Increments the count of occurrences for a given year.
     *
     * @param year The year to increment the count for.
     */
    void incrementYearCount(String year);

    /**
     * Increments the count of occurrences for each color specified.
     * Multiple colors can be included in a single string, separated by commas.
     *
     * @param color The comma-separated color(s) to increment the count for.
     */
    void incrementColorCount(String color);
    ConcurrentHashMap<String, Integer> getBrandStatistics();
    ConcurrentHashMap<String, Integer> getYearStatistics();
    ConcurrentHashMap<String, Integer> getColorStatistics();
}
