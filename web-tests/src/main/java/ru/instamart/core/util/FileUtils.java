package ru.instamart.core.util;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public final class FileUtils {

    public static String getResourceDir(final String fileName) {
        return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(fileName)).getPath();
    }

    public static File[] foundFile(final String path, final String prefix) {
        final File dir = new File(path);
        return dir.listFiles((dir1, name) -> name.startsWith(prefix));
    }
}
