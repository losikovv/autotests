package ru.instamart.kraken.enums;

import java.util.Arrays;

public enum Tenant {

    METRO,
    OKEY,
    SELGROS,
    SBERMARKET,
    OTHER;

    public static Tenant getValue(final String constant) {
        return Arrays.stream(Tenant.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(OTHER);
    }
}
