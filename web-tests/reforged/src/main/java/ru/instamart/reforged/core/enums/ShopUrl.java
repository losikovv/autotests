package ru.instamart.reforged.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.config.UiProperties;

@Getter
@RequiredArgsConstructor
public enum ShopUrl {

    DEFAULT(UiProperties.DEFAULT_RETAILER),
    METRO("metro"),
    LENTA("lenta"),
    AUCHAN("auchan"),
    VKUSVILL("vkusvill"),
    AZBUKAVKUSA("azbukavkusa"),
    OKEY("okey");

    private final String url;
}
