package ru.instamart.reforged.stf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipmentStates {
    ACCEPTED("Принят");

    private final String name;
}
