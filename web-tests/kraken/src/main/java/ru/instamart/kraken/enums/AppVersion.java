package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum AppVersion {

    WEB("web", ""),
    IOS("SbermarketIOS", "6.29.0"),
    ANDROID("SbermarketAndroid", "6.29.0");

    private final String name;
    private final String version;
}
