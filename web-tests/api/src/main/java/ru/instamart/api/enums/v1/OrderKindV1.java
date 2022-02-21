package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum OrderKindV1 {
    HOME("home"),
    OFFICE("office");

    private final String value;
}
