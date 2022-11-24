package ru.instamart.kraken.helper;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public final class AllureHelper {

    private static final String PATH = System.getProperty("user.dir") + "/build/allure-results/";
    private static final String ENV_FILE_NAME = "environment.xml";

    public static void allureEnvironmentWriter(final Map<String, String> envValueSet) {
        try {
            final var dbf = DocumentBuilderFactory.newInstance();
            final var db = dbf.newDocumentBuilder();
            final var doc = db.newDocument();
            final var environment = doc.createElement("environment");
            doc.appendChild(environment);
            envValueSet.forEach((k, v) -> environment.appendChild(makeNode(k, v, doc)));

            Files.createDirectories(Paths.get(PATH));
            writeXml(doc);
            log.debug("Allure environment data saved.");
        } catch (ParserConfigurationException pce) {
            log.error("[allureEnvironmentWriter] error while parsing={}", envValueSet, pce);
        } catch (TransformerException tfe) {
            log.error("[allureEnvironmentWriter] error while transform={}", envValueSet, tfe);
        } catch (IOException e) {
            log.error("[allureEnvironmentWriter] error while creating directory={}", PATH);
        }
    }

    public static void allureEnvironmentUpdate(final Map<String, String> envValueSet) {
        final var dbf = DocumentBuilderFactory.newInstance();
        try(final var is = new FileInputStream(PATH + ENV_FILE_NAME)) {
            final var db = dbf.newDocumentBuilder();
            final var doc = db.parse(is);
            final var environment = doc.getElementsByTagName("environment").item(0);
            envValueSet.forEach((k, v) -> environment.appendChild(makeNode(k, v, doc)));
            writeXml(doc);
        } catch (FileNotFoundException fnfe) {
            log.error("[allureEnvironmentUpdate] error while open file={}", ENV_FILE_NAME, fnfe);
        } catch (IOException io) {
            log.error("[allureEnvironmentUpdate] error while read file={}", ENV_FILE_NAME, io);
        } catch (ParserConfigurationException pce) {
            log.error("[allureEnvironmentUpdate] error while parse file={}", ENV_FILE_NAME, pce);
        } catch (SAXException sax) {
            log.error("[allureEnvironmentUpdate] error while append node to file={}", ENV_FILE_NAME, sax);
        } catch (TransformerException te) {
            log.error("[allureEnvironmentUpdate] error while transform={}", envValueSet, te);
        }
    }

    private static Element makeNode(final String k, final String v, final Document doc) {
        final var parameter = doc.createElement("parameter");
        final var key = doc.createElement("key");
        final var value = doc.createElement("value");

        key.appendChild(doc.createTextNode(k));
        value.appendChild(doc.createTextNode(v));
        parameter.appendChild(key);
        parameter.appendChild(value);

        return parameter;
    }

    private static void writeXml(final Document doc) throws TransformerException, IOException {
        final var transformerFactory = TransformerFactory.newInstance();
        final var transformer = transformerFactory.newTransformer();
        final var source = new DOMSource(doc);
        final var result = new StreamResult(new File(PATH + ENV_FILE_NAME));

        transformer.transform(source, result);
    }
}
