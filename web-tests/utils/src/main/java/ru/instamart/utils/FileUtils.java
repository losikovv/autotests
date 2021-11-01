package ru.instamart.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

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
            return "json_error";
        }
    }

    public static void writeStringToFile(String string, String filePath) {
        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
