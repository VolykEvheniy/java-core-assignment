package com.vlkevheniy;

import static org.junit.jupiter.api.Assertions.*;

import com.vlkevheniy.java_core_task.output.GeneratingXmlException;
import com.vlkevheniy.java_core_task.output.XmlOutputGeneratorImpl;
import org.junit.jupiter.api.Test;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class XmlOutputGeneratorImplTest {

    @Test
    void testGenerateXmlCreatesCorrectFile() throws Exception {
        // Given
        XmlOutputGeneratorImpl generator = new XmlOutputGeneratorImpl();
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("Toyota", 3);
        statistics.put("Honda", 2);

        File outputFile = File.createTempFile("statistics", ".xml");

        // When
        generator.generateXml(statistics, "statistics", outputFile);

        // Then
        assertTrue(outputFile.exists());
        String content = Files.readString(outputFile.toPath(), StandardCharsets.UTF_8);
        assertTrue(content.contains("<value>Toyota</value>"));
        assertTrue(content.contains("<count>3</count>"));
        assertTrue(content.contains("<value>Honda</value>"));
        assertTrue(content.contains("<count>2</count>"));

        assertTrue(outputFile.delete());
    }

    @Test
    void testGenerateXml_JAXBException() throws Exception {
        // Given
        XmlOutputGeneratorImpl generator = new XmlOutputGeneratorImpl();
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("Toyota", 10);

        File outputFile = new File("/invalid/path/statistics.xml");

        // When and Then
        assertThrows(GeneratingXmlException.class, () -> generator.generateXml(statistics, "statistics", outputFile),
                "The test should throw a GeneratingXmlException due to JAXB issues.");
    }
}
