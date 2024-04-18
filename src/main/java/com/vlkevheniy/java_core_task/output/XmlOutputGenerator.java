package com.vlkevheniy.java_core_task.output;

import java.io.File;
import java.util.Map;

/**
 * Interface for generating XML output from statistics data.
 */
public interface XmlOutputGenerator {

    /**
     * Generates an XML file with statistics data.
     *
     * @param statistics      A map of statistics where the key is the item name and the value is the count.
     * @param rootElementName The name of the root element in the generated XML.
     * @param outputFile      The file to which the XML content will be written.
     * @throws GeneratingXmlException if there is an issue with generating the XML output.
     */
    void generateXml(Map<String, Integer> statistics, String rootElementName, File outputFile) throws GeneratingXmlException;
}
