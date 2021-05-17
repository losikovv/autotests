package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum AssemblyStateSHP {

    COLLECTING("collecting"),
    PAYMENT_VERIFICATION("payment_verification"),
    ASSEMBLED("assembled"),
    ON_CASH_DESK("on_cash_desk"),
    PACKAGING("packaging"),
    READY_TO_SHIP("ready_to_ship"),
    PAUSED("paused"),
    ON_APPROVAL("on_approval"),
    SUSPENDED("suspended"),
    SHIPPED("shipped");

    @Getter
    private final String state;
}
