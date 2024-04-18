package com.vlkevheniy;

import com.vlkevheniy.java_core_task.statistics.StatisticsCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsCalculatorImplTest {

    private StatisticsCalculatorImpl statisticsCalculator;

    @BeforeEach
    void setUp() {
        statisticsCalculator = new StatisticsCalculatorImpl();
    }

    @Test
    void incrementBrandCount_ShouldAccumulateCorrectly() {
        // Given
        String brand = "Toyota";

        // When
        statisticsCalculator.incrementBrandCount(brand);
        statisticsCalculator.incrementBrandCount(brand);

        // Then
        assertEquals(2, statisticsCalculator.getBrandStatistics().get(brand));
    }

    @Test
    void incrementYearCount_ShouldAccumulateCorrectly() {
        // Given
        String year = "2022";

        // When
        statisticsCalculator.incrementYearCount(year);
        statisticsCalculator.incrementYearCount(year);

        // Then
        assertEquals(2, statisticsCalculator.getYearStatistics().get(year));
    }

    @Test
    void incrementColorCount_ShouldAccumulateCorrectly() {
        // Given
        String color = "Red, Blue";

        // When
        statisticsCalculator.incrementColorCount(color);
        statisticsCalculator.incrementColorCount("Blue");

        // Then
        assertEquals(1, statisticsCalculator.getColorStatistics().get("Red"));
        assertEquals(2, statisticsCalculator.getColorStatistics().get("Blue"));
    }
}
