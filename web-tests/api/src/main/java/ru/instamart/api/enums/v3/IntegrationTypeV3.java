package ru.instamart.api.enums.v3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum IntegrationTypeV3 {
    SHOPPER("0"),
    DELIVERY_BY_SBERMARKET("3"),
    INTEGRATION_FOR_ACCOUNTING("4"),
    DELIVERY_BY_RETAILER("5"),
    ACCOUNTING_WITH_PICKUP("6");
    private final String value;
}
