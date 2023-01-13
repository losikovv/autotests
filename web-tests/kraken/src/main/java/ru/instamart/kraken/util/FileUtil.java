package ru.instamart.kraken.util;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public final class FileUtil {

    public static String getResourceDir(final String fileName) {
        return Objects.requireNonNull(FileUtil.class.getClassLoader().getResource(fileName)).getPath();
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

    @Step("Скачиваем файл")
    public static Path saveFile(Response response, String fileName) {
        try {
            final var path = Paths.get("build", "tmp", fileName);

            FileUtils.copyInputStreamToFile(response.asInputStream(), path.toFile());
            return path;
        } catch (IOException e) {
            log.error("Не удалось сохранить файл");
        }
        return null;
    }

    public static byte[] readFile(File xlsFile) {
        try {
            return FileUtils.readFileToByteArray(Paths.get(xlsFile.getAbsolutePath()).toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read file " + xlsFile, e);
        }
    }

    public static byte[] readBytes(InputStream inputStream) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            int nRead;
            while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                result.write(buffer, 0, nRead);
            }

            return result.toByteArray();
        } catch (IOException e) {
            log.error("Error read inputStream");
        }
        return null;
    }

    public static byte[] readBytes(URL url) {
        try (InputStream inputStream = url.openStream()) {
            return readBytes(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readZipFile(Path file) {
        try {
            ZipInputStream is = new ZipInputStream(FileUtils.openInputStream(file.toFile()));
            ZipEntry entry;
            final var list = new ArrayList<String>();
            while ((entry = is.getNextEntry()) != null) {
                list.add(entry.getName());
            }
            is.close();
            return list;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void unzipFolder(Path source, Path target) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }

                Path newPath = zipSlipProtect(zipEntry, target);

                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir) {
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            log.error("Bad zip entry: {}", zipEntry.getName());
        }
        return normalizePath;
    }

    public static List<Path> getAllFileDirectory(Path path) {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
