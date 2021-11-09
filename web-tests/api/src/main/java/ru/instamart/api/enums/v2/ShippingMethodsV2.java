package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ShippingMethodsV2 {
    PICKUP("pickup"),
    BY_COURIER("by_courier");

    private final String method;

}
