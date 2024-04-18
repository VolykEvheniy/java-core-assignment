package com.vlkevheniy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.vlkevheniy.java_core_task.parsing.CarParserImpl;
import com.vlkevheniy.java_core_task.parsing.ParsingCarException;
import com.vlkevheniy.java_core_task.statistics.StatisticsCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
public class CarParserImplTest {

    @Mock
    private StatisticsCalculator statisticsCalculator;

    private CarParserImpl carParser;

    @BeforeEach
    void setUp() {
        carParser = new CarParserImpl(statisticsCalculator);
    }

    @Test
    void testParseValidJsonFile() throws Exception {
        // Given
        String json = "[" +
                "{\"year\":2022,\"brand\":\"Toyota\",\"color\":\"White, Black\"}," +
                "{\"year\":2021,\"brand\":\"Honda\",\"color\":\"Red, Grey\"}" +
                "]";
        Path tempPath = Files.createTempFile("test", ".json");
        Files.write(tempPath, json.getBytes());

        // When
        carParser.parseFile(tempPath.toFile());

        // Then
        verify(statisticsCalculator).incrementBrandCount("Toyota");
        verify(statisticsCalculator).incrementYearCount("2022");
        verify(statisticsCalculator).incrementColorCount("White, Black");
        verify(statisticsCalculator).incrementBrandCount("Honda");
        verify(statisticsCalculator).incrementYearCount("2021");
        verify(statisticsCalculator).incrementColorCount("Red, Grey");

        Files.deleteIfExists(tempPath);
    }

    @Test
    void testParseFile_MalformedJson() throws Exception {
        // Given
        String malformedJson = "[{\"year\":2022,\"brand\":\"Toyota\",\"color\":\"White, Black\"";  // Malformed JSON
        File tempFile = File.createTempFile("malformed", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(malformedJson);
        }

        // When and Then
        assertThrows(ParsingCarException.class, () -> carParser.parseFile(tempFile),
                "The test should throw a ParsingCarException due to malformed JSON.");

        // Cleanup
        tempFile.delete();
    }
}
