package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CiModule {

    UI_STF("ui-stf"),
    UI_B2B("ui-b2b"),
    UI_ADMIN("ui-admin"),
    API("api"),
    GRPC("grpc"),
    UNKNOWN("unknown");

    private static final CiModule CI_MODULE = getValue(System.getenv("CI_MODULE"));
    private final String name;

    public static CiModule getValue(final String constant) {
        return Arrays.stream(CiModule.values())
                .filter(e -> e.getName().equalsIgnoreCase(constant))
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
        return CI_MODULE == UI_B2B;
    }

    public static boolean isAdmin() {
        return CI_MODULE == UI_ADMIN;
    }

    public static boolean isApi() {
        return CI_MODULE == API;
    }

    public static boolean isGrpc() {
        return CI_MODULE == GRPC;
    }
}
