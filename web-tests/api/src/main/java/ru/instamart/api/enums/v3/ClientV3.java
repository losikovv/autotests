package ru.instamart.api.enums.v3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ClientV3 {
    METRO_MARKETPLACE("metro_marketplace"),
    AUCHAN("auchan"),
    SBER_DEVICES("sber_devices"),
    GOODS("goods"),
    ALIEXPRESS("aliexpress");

    private final String name;
}
