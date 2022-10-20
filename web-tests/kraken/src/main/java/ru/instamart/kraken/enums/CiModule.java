package ru.instamart.kraken.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CiModule {

    UI_STF("ui-stf"),
    UI_B2B("ui-b2b"),
    API("api"),
    GRPC("grpc");

    private final String name;
}
