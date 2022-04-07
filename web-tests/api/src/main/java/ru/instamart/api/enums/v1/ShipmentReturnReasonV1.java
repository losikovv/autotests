package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ShipmentReturnReasonV1 {
    FAULTY_PRODUCT_QUALITY("faulty_product_quality"),
    REFUSED_POSITION("refused_position"),
    NOT_BRINGED_PRODUCT("not_bringed_product"),
    WRONG_PRODUCT("wrong_product"),
    ORDER_CANCELLED("order_cancelled"),
    SERVICE_GUARANTEE("service_guarantee");

    private final String id;
}
