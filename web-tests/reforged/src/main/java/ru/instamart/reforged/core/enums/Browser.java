package ru.instamart.reforged.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@ToString
public enum Browser {

    CHROME("chrome"),
    CHROME_REMOTE("chrome_remote"),

    FIREFOX("firefox"),
    FIREFOX_REMOTE("firefox_remote"),

    SAFARI("safari"),
    IE("ie"),
    OPERA("opera"),

    DEFAULT("chrome_remote");

    private final String name;

    public static Browser getValue(final String constant) {
        return Arrays.stream(Browser.values())
                .filter(e -> e.getName().equals(constant))
                .findFirst()
                .orElse(DEFAULT);
    }
}
