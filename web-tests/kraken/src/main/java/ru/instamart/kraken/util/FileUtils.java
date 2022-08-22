package ru.instamart.kraken.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public final class FileUtils {

    public static String getResourceDir(final String fileName) {
        return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(fileName)).getPath();
    }

    public static InputStream getConfig(final String configName, final Class<?> configClass) {
        return Objects.requireNonNull(configClass.getClassLoader().getResourceAsStream(configName));
    }

    public static File[] foundFiles(final String path, final String prefix) {
        final File dir = Paths.get(".").resolve(path).toFile();
        return dir.listFiles((dir1, name) -> name.startsWith(prefix));
    }

    public static String getJson(final String jsonPath, final Class<?> configClass) {
        try (var lnr = new BufferedReader(new InputStreamReader(getConfig(jsonPath, configClass), StandardCharsets.UTF_8))) {
            return lnr.lines().collect(Collectors.joining());
        } catch (Exception e) {
            log.error("FATAL: Can't get json='{}' for class='{}'", jsonPath, configClass);
            return "json_error";
        }
    }

    public static void writeStringToFile(String string, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(string);
        } catch (IOException e) {
            log.error("FATAL: Can't write string='{}' to file='{}'", string, filePath);
        }
    }

    public static byte[] changeXlsFileSheetName(String pathToFile, String name, int sheetNumber) {
        byte[] fileBytes = null;
        try (FileInputStream inputStream = new FileInputStream(pathToFile);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            workbook.setSheetName(sheetNumber, name);
            workbook.write(outputStream);
            fileBytes = outputStream.toByteArray();
        } catch (IOException e) {
            log.error("FATAL: Can't get bytes from file='{}'", pathToFile);
        }
        return fileBytes;
    }
}
