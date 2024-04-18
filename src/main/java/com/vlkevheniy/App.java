package com.vlkevheniy;

import com.vlkevheniy.java_core_task.output.GeneratingXmlException;
import com.vlkevheniy.java_core_task.output.XmlOutputGenerator;
import com.vlkevheniy.java_core_task.output.XmlOutputGeneratorImpl;
import com.vlkevheniy.java_core_task.parsing.CarParser;
import com.vlkevheniy.java_core_task.parsing.CarParserImpl;
import com.vlkevheniy.java_core_task.parsing.ParsingCarException;
import com.vlkevheniy.java_core_task.statistics.StatisticsCalculator;
import com.vlkevheniy.java_core_task.statistics.StatisticsCalculatorImpl;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main application class responsible for initializing and coordinating the car data parsing and XML generation process.
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        final int THREAD_POOL_SIZE = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        StatisticsCalculator statisticsCalculator = new StatisticsCalculatorImpl();

        long startTime = System.nanoTime();

        try {
            processFiles(executorService, statisticsCalculator);
        } finally {
            shutdownExecutor(executorService);
        }
        generateOutput(statisticsCalculator);

        long endTime = System.nanoTime(); // End timing
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        logger.info("Time taken with " + THREAD_POOL_SIZE + " threads: " + duration + " ms");
    }

    private static void processFiles(ExecutorService executorService, StatisticsCalculator statisticsCalculator) {
        File folder = new File("src/main/resources/data");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                CarParser carParser = new CarParserImpl(statisticsCalculator);
                executorService.execute(() -> {
                    try {
                        carParser.parseFile(file);
                    } catch (ParsingCarException e) {
                        logger.error("Parsing error occurred for file: " + file.getName(), e);
                    }
                });
            }
        }
    }

    private static void shutdownExecutor(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.MINUTES)) {
                logger.warn("Not all tasks finished within the time limit, forcing shutdown...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            logger.error("Executor interrupted, forced shutdown initiated", e);
        }
    }

    private static void generateOutput(StatisticsCalculator statisticsCalculator) {
        File resultsDir = new File("results");
        if (!resultsDir.exists() && !resultsDir.mkdir()) {
            logger.error("Failed to create results directory");
            return;
        }

        XmlOutputGenerator xmlOutputGenerator = new XmlOutputGeneratorImpl();
        try {
            xmlOutputGenerator.generateXml(statisticsCalculator.getBrandStatistics(), "brand", new File(resultsDir, "statistics_by_brand.xml"));
            xmlOutputGenerator.generateXml(statisticsCalculator.getYearStatistics(), "year", new File(resultsDir,"statistics_by_year.xml"));
            xmlOutputGenerator.generateXml(statisticsCalculator.getColorStatistics(), "color", new File(resultsDir,"statistics_by_color.xml"));
        } catch (GeneratingXmlException e) {
            logger.error("Failed to generate XML output", e);
        }
    }
}
