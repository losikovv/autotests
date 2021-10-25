package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ProductPriceTypeV2 {

    PER_ITEM("per_item"),
    PER_PACK("per_pack");

    private final String value;
}
