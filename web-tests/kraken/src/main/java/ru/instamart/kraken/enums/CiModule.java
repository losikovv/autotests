package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Getter
public enum CiModule {

    UI_STF("ui-stf"),
    UI_B2B("ui-b2b"),
    API("api"),
    GRPC("grpc");

    private static final CiModule CI_MODULE = getValue(System.getenv("CI_MODULE"));
    private final String name;

    public static CiModule getValue(final String constant) {
        return Arrays.stream(CiModule.values())
                .filter(e -> e.name().equalsIgnoreCase(constant))
                .findFirst()
                .orElse(null);
    }

    public static boolean isUi() {
        return isStf() || isB2b();
    }

    public static boolean isStf() {
        if (isNull(CI_MODULE)) return false;
        return CI_MODULE == UI_STF;
    }

    public static boolean isB2b() {
        if (isNull(CI_MODULE)) return false;
        return CI_MODULE == UI_STF;
    }

    public static boolean isApi() {
        if (isNull(CI_MODULE)) return false;
        return CI_MODULE == API;
    }

    public static boolean isGrpc() {
        if (isNull(CI_MODULE)) return false;
        return CI_MODULE == GRPC;
    }

    public static void main(String[] args) {
        System.out.println(isUi());
        System.out.println(CI_MODULE);
    }
}
