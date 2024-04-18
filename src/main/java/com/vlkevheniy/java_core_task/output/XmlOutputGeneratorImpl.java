package com.vlkevheniy.java_core_task.output;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of {@link XmlOutputGenerator} for generating XML output from statistics data.
 */
public class XmlOutputGeneratorImpl implements XmlOutputGenerator {

    @Override
    public void generateXml(Map<String, Integer> statistics, String rootElementName, File outputFile) throws GeneratingXmlException {
        Statistics stats = new Statistics();
        stats.setItems(statistics.entrySet().stream()
                .map(e -> new Statistics.Item(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Statistics.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(stats, outputFile);
        } catch (JAXBException e) {
            throw new GeneratingXmlException("Failed to generate XML output", e);
        }
    }
}
