package ru.instamart.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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

    public static String getJson(final String json) throws IOException {
        return Files.readString(Path.of(getResourceDir(json)));
    }
}
