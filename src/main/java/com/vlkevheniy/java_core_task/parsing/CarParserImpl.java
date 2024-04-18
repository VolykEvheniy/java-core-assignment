package com.vlkevheniy.java_core_task.parsing;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.vlkevheniy.java_core_task.statistics.StatisticsCalculator;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of {@link CarParser} for parsing car data from JSON files.
 */
public class CarParserImpl implements CarParser{

    private final StatisticsCalculator statisticsCalculator;
    private final JsonFactory jsonFactory = new JsonFactory();

    public CarParserImpl(StatisticsCalculator statisticsCalculator) {
        this.statisticsCalculator = statisticsCalculator;
    }

    @Override
    public void parseFile(File jsonFile) throws ParsingCarException {
        try(JsonParser parser = jsonFactory.createParser(jsonFile)) {
            if(parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Expected start of array in the JSON file");
            }

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if(parser.getCurrentToken() == JsonToken.START_OBJECT) {
                    parseCarObject(parser);
                }
            }
        } catch (IOException e) {
            throw new ParsingCarException("Error reading JSON file: " + jsonFile.getPath(), e);
        }
    }

    private void parseCarObject(JsonParser parser) throws ParsingCarException {
        try {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                if (fieldName != null) {
                    parser.nextToken();
                    switch (fieldName) {
                        case "brand":
                            String brand = parser.getText();
                            statisticsCalculator.incrementBrandCount(brand);
                            break;
                        case "year":
                            int year = parser.getIntValue();
                            statisticsCalculator.incrementYearCount(String.valueOf(year));
                            break;
                        case "color":
                            String color = parser.getText();
                            statisticsCalculator.incrementColorCount(color);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            throw new ParsingCarException("Error parsing car object", e);
        }
    }
}
