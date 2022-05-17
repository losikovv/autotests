package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
public enum RoleSHP {

    SHOPPER("shopper"),
    DRIVER("driver"),
    UNIVERSAL("universal"),
    AUTO_UNIVERSAL("auto_universal"),
    PACKER("packer"),
    EXTERNAL_INSPECTOR("external_inspector");

    @Getter
    private final String role;

    public static Stream<RoleSHP> stream() {
        return Stream.of(RoleSHP.values());
    }
}
