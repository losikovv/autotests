package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum AnalyzeResultV2 {
    ALL_PRODUCTS_DISAPPEARS("all_products_disappears"),
    OTHER_RETAILER_SHIPMENTS_DISAPPEARS("other_retailer_shipments_disappears"),
    ALCOHOL_DISAPPEARS("alcohol_disappears"),
    OK("ok");

    private final String value;
}
