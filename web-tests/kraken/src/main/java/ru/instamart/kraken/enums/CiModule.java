package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CiModule {

    UI_STF("ui-stf"),
    UI_B2B("ui-b2b"),
    API("api"),
    GRPC("grpc"),
    UNKNOWN("unknown");

    private static final CiModule CI_MODULE = getValue(System.getenv("CI_MODULE"));
    private final String name;

    public static CiModule getValue(final String constant) {
        return Arrays.stream(CiModule.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public static boolean isUi() {
        return isStf() || isB2b();
    }

    public static boolean isStf() {
        return CI_MODULE == UI_STF;
    }

    public static boolean isB2b() {
        return CI_MODULE == UI_STF;
    }

    public static boolean isApi() {
        return CI_MODULE == API;
    }

    public static boolean isGrpc() {
        return CI_MODULE == GRPC;
    }
}
