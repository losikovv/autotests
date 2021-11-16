package ru.instamart.reforged.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.kraken.config.CoreProperties;

@Getter
@RequiredArgsConstructor
public enum ShopUrl {

    DEFAULT(CoreProperties.DEFAULT_RETAILER),
    METRO("metro"),
    LENTA("lenta"),
    AUCHAN("auchan"),
    VKUSVILL("vkusvill"),
    AZBUKAVKUSA("azbukavkusa"),
    OKEY("okey");


    private final String url;
}
