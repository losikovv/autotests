package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum AppVersion {

    WEB("web", "102.0"),
    IOS("ios", "65.0"),
    ANDROID("android", "65.0");

    private final String name;
    private final String version;
}
