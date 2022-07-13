package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum PricerSHP {
    PER_ITEM("per_item"),
    PER_ITEMS_PACKAGE("per_items_package"),
    PER_WEIGHT_PACKAGE("per_weight_package"),
    PER_WEIGHT("per_weight");

    @Getter
    private final String type;
}
