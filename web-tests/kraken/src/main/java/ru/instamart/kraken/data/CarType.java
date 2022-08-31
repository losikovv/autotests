package ru.instamart.kraken.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CarType {

    car("Легковой автомобиль"),
    truck("Грузовой автомобиль"),
    bicycle("Велосипед"),
    scooter("Самокат"),
    electricbicycle("Электровелосипед"),
    other_transport("Другой транспорт");

    private final String name;
}
