package ru.instamart.reforged.stf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethods {
    BY_NEW_CARD_ONLINE("Новой картой онлайн"),
    BY_CARD_ONLINE("Картой онлайн"),
    BY_CARD_TO_COURIER("Картой курьеру"),
    BY_CARD_UPON_RECEIPT("Картой при получении"),
    BY_CARD_AT_CHECKOUT("Картой на кассе"),
    BY_CASH("Наличными"),
    BY_BUSINESS_ACCOUNT("По счёту для бизнеса"),
    SBERSPASIBO_BONUSES("Бонусы от СберСпасибо");

    private final String name;
}
