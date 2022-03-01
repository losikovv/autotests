package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum PaymentStatusV1 {
    PAID("paid"),
    NOT_PAID("not_paid"),
    BALANCE_DUE("balance_due"),
    OVERPAID("overpaid"),
    FAILED("failed");

    private final String value;
}
