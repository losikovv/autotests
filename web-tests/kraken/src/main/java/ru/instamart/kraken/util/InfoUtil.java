package ru.instamart.kraken.util;

public final class InfoUtil {

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static boolean isMac() {
        return getOsName().contains("Mac");
    }

    public static boolean isWin() {
        return getOsName().contains("Win");
    }

    public static boolean isLinux() {
        return getOsName().contains("Linux");
    }

    private InfoUtil() {}
}
