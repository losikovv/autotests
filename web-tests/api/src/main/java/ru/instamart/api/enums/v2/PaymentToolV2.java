package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@ToString
@Getter
public enum PaymentToolV2 {
    LIFEPAY("lifepay", "Картой курьеру"),
    CASH_DESK("cash_desk", "На кассе"),
    EXTERNAL_PARTNER_PAY("external_partner_pay", "Внешний платеж через партнера"),
    SBER_GATEWAY("sber_gateway", ""),
    SBER_APP_PAY("sber_app_pay", "Платеж через СберАпп");


    private final String key;
    private final String name;

    public static Optional<PaymentToolV2> getIfKeyIsPresent(String str) {
        return Arrays.stream(PaymentToolV2.values())
                .filter(payment -> str.equals(payment.getKey()))
                .findFirst();
    }

    public static Optional<PaymentToolV2> getIfNameIsPresent(String str) {
        return Arrays.stream(PaymentToolV2.values())
                .filter(payment -> str.equals(payment.getName()))
                .findFirst();
    }
}
