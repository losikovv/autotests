package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum OrderStatusV2 {

    ACTIVE("active"),
    READY("ready"),
    COLLECTING("collecting"),
    READY_TO_SHIP("ready_to_ship"),
    COMPLETE("complete"),
    SHIPPED("shipped"),
    CANCELED("canceled");

    @Getter
    private final String status;
}
