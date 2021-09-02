package ru.instamart.reforged.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.kraken.setting.Config;

@Getter
@RequiredArgsConstructor
public enum ShopUrl {

    DEFAULT(Config.DEFAULT_RETAILER),
    METRO("metro"),
    LENTA("lenta"),
    AUCHAN("auchan"),
    VKUSVILL("vkusvill");

    private final String url;
}
