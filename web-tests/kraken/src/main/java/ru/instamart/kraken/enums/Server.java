package ru.instamart.kraken.enums;

import java.util.Arrays;

public enum Server {

    PREPROD,
    PRODUCTION,
    STAGING,
    CUSTOM,
    OTHER;

    public static Server getValue(final String constant) {
        return Arrays.stream(Server.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(OTHER);
    }
}
