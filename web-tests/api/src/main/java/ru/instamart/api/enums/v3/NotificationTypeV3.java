package ru.instamart.api.enums.v3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum NotificationTypeV3 {
    IN_WORK("order.in_work"),
    ASSEMBLED("order.assembled"),
    READY_FOR_DELIVERY("order.ready_for_delivery"),
    DELIVERED("order.delivered"),
    CANCELED("order.canceled");

    private final String value;
}
