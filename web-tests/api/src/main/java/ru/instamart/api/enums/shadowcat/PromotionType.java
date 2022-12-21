package ru.instamart.api.enums.shadowcat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PromotionType {
    FREE_DELIVERY(2, "krakendel"),
    DISCOUNT(3, "krakendisc"),
    SBERSPASIBO(1299, "krakenspas");

    @Getter
    private final int id;

    @Getter
    private final String code;
}
