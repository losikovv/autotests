package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ShippingMethodV2 {
    PICKUP("pickup"),
    BY_COURIER("by_courier"),
    BY_COURIER_FOR_COMPANIES("by_courier_for_companies");

    private final String method;

}
