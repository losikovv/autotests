package ru.instamart.kraken.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ShoppersRole {

    //Сборщик Курьер Упаковщик Контроллёр Универсал
    //Collector Courier Packer Controller Universal
    COLLECTOR("Сборщик"),
    COURIER("Курьер"),
    PACKER("Упаковщик"),
    CONTROLLER("Контроллёр"),
    UNIVERSAL("Универсал");

    private final String data;

    public int getValue() {
        return ordinal() + 1;
    }
}
