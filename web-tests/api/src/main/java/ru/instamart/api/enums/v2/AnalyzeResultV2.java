package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum AnalyzeResultV2 {
    ALL_PRODUCTS_DISAPPEARS("all_products_disappears"),
    OTHER_RETAILER_SHIPMENT_DISAPPEARS("other_reatailer_shipment_disappears"),
    ALCOHOL_DISAPPEARS("alcohol_disappears"),
    OK("ok");

    private final String value;
}
