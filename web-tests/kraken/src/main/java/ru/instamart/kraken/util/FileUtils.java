package ru.instamart.kraken.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class FileUtils {

    public static String getResourceDir(final String fileName) {
        return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(fileName)).getPath();
    }

    public static File[] foundFile(final String path, final String prefix) {
        final File dir = new File(path);
        return dir.listFiles((dir1, name) -> name.startsWith(prefix));
    }

    public static String getJson(final String json) throws IOException {
        return Files.readString(Path.of(getResourceDir(json)));
    }
}
