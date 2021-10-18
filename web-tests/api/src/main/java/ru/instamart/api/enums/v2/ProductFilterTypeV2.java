package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ProductFilterTypeV2 {

    DISCOUNTED("discounted"),
    BRAND("brand"),
    COUNTRY("country");

    private final String value;
}
