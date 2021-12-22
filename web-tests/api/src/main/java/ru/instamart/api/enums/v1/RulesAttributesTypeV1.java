package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum RulesAttributesTypeV1{
    DAYS_FROM_NOW("ShippingPolicy::Rules::DaysFromNow"),
    TIME_BEFORE_SHIPMENT("ShippingPolicy::Rules::TimeBeforeShipment"),
    TIME_FROM_MIDNIGHT("ShippingPolicy::Rules::TimeFromMidnight"),
    DELIVERY_WINDOW_NUMBER("ShippingPolicy::Rules::DeliveryWindowNumber"),
    TIME_BEFORE_EXPRESS_SHIPMENT("ShippingPolicy::Rules::TimeBeforeExpressShipment");

    private final String value;
}