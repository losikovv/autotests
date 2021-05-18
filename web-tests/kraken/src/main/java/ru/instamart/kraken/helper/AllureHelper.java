package ru.instamart.kraken.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class AllureHelper {

    private static final Logger log = LoggerFactory.getLogger(AllureHelper.class);

    public static void allureEnvironmentWriter(Map<String, String> environmentValuesSet)  {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element environment = doc.createElement("environment");
            doc.appendChild(environment);
            environmentValuesSet.forEach((k, v) -> {
                Element parameter = doc.createElement("parameter");
                Element key = doc.createElement("key");
                Element value = doc.createElement("value");
                key.appendChild(doc.createTextNode(k));
                value.appendChild(doc.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File allureResultsDir = new File( System.getProperty("user.dir")
                    + "/target/allure-results");
            if (!allureResultsDir.exists()) allureResultsDir.mkdirs();
            StreamResult result = new StreamResult(
                    new File( System.getProperty("user.dir")
                            + "/target/allure-results/environment.xml"));
            transformer.transform(source, result);
            log.info("Allure environment data saved.");
        } catch (ParserConfigurationException pce) {
            log.error("[allureEnvironmentWriter] error while parsing={}", environmentValuesSet, pce);
        } catch (TransformerException tfe) {
            log.error("[allureEnvironmentWriter] error while transform={}", environmentValuesSet, tfe);
        }
    }

    public static void allureEnvironmentWriter(Map<String, String> environmentValuesSet,
                                               String customResultsPath)  {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element environment = doc.createElement("environment");
            doc.appendChild(environment);
            environmentValuesSet.forEach((k, v) -> {
                Element parameter = doc.createElement("parameter");
                Element key = doc.createElement("key");
                Element value = doc.createElement("value");
                key.appendChild(doc.createTextNode(k));
                value.appendChild(doc.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File allureResultsDir = new File(customResultsPath);
            if (!allureResultsDir.exists()) allureResultsDir.mkdirs();
            StreamResult result = new StreamResult(
                    new File( customResultsPath + "environment.xml"));
            transformer.transform(source, result);
            log.info("Allure environment data saved.");
        } catch (ParserConfigurationException pce) {
            log.error("[allureEnvironmentWriter] error while parsing={}", environmentValuesSet, pce);
        } catch (TransformerException tfe) {
            log.error("[allureEnvironmentWriter] error while transform={}", environmentValuesSet, tfe);
        }
    }
}
