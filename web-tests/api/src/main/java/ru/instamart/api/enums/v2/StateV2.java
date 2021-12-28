package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StateV2 {
    PENDING("pending"),
    ASSEMBLED("assembled"),
    CANCELED("canceled"),
    REPLACED("replaced"),
    READY("ready");

    private final String value;
}
