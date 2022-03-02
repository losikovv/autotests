package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@RequiredArgsConstructor
@ToString
@Getter
public enum Tenant {

    METRO("metro"),
    OKEY("okey"),
    SELGROS("selgros"),
    SBERMARKET("sbermarket"),
    AUCHAN("auchan"),
    BUSINESS("business"),
    OTHER("other");

    private final String id;

    public static Tenant getValue(final String constant) {
        return Arrays.stream(Tenant.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(OTHER);
    }
}
