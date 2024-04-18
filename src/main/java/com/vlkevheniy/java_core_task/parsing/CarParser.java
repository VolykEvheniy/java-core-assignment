package com.vlkevheniy.java_core_task.parsing;

import java.io.File;

/**
 * Interface for parsing car information from a file.
 */
public interface CarParser {
    /**
     * Parses a JSON file containing car data and increments relevant statistics.
     *
     * @param jsonFile The JSON file to parse.
     * @throws ParsingCarException if there is an issue with parsing the file.
     */
    void parseFile(File jsonFile) throws ParsingCarException;
}
