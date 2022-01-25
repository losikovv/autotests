package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ProductPriceTypeV2 {

    PER_ITEM("per_item"),
    PER_KILO("per_kilo"),
    PER_PACK("per_pack"),
    PER_PACKAGE("per_package");

    private final String value;
}
