package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ImportStatusV1 {

    PENDING("pending"),
    PROCESSING("processing"),
    DONE("done");

    private final String value;
}
