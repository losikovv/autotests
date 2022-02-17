package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StateV2 {
    PENDING("pending"),
    ASSEMBLED("assembled"),
    CANCELED("canceled"),
    REPLACED("replaced"),
    READY("ready"),
    COLLECTING("collecting"),
    READY_TO_SHIP("ready_to_ship"),
    SHIPPING("shipping"),
    SHIPPED("shipped");

    private final String value;
}
