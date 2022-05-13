package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ToString
public enum RoleSHP {

    SHOPPER("shopper", 1),
    DRIVER("driver", 2),
    UNIVERSAL("universal", 5),
    AUTO_UNIVERSAL("auto_universal", 4);

    @Getter
    private final String role;
    @Getter
    private final int roleId;

    public static Stream<RoleSHP> stream() {
        return Stream.of(RoleSHP.values());
    }
}
