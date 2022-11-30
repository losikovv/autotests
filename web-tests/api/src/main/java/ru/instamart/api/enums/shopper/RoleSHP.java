package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
public enum RoleSHP {

    SHOPPER("shopper",0),
    DRIVER("driver", 1),
    UNIVERSAL("universal",2),
    AUTO_UNIVERSAL("auto_universal",3),
    PACKER("packer", 4),
    EXTERNAL_INSPECTOR("external_inspector", 5);

    @Getter
    private final String role;
    @Getter
    private final Integer id;

    public static Stream<RoleSHP> stream() {
        return Stream.of(RoleSHP.values());
    }
}
