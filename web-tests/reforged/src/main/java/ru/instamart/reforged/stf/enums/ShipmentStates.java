package ru.instamart.reforged.stf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipmentStates {
    ACCEPTED_STATE("Принят"),
    COLLECTING_STATE("Собираем"),
    READY_TO_SHIP_STATE("Скоро отправим"),
    SHIPPING_STATE("В пути"),
    SHIPPED_STATE("Доставлен"),
    RECEIVED_STATE("Получен");

    private final String name;
}
