package ru.instamart.reforged.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethods {
    BY_CASH("Наличные"),
    BY_CARD_TO_COURIER("Картой курьеру"),
    AT_CHECKOUT("На кассе");
    private String name;
}
