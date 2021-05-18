package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum PackageSetLocationSHP {

    BASKET("basket",1),
    RACK("rack",2),
    FRIDGE("fridge",3),
    FREEZER("freezer",4);

    private final String location;
    private final int boxNumber;
}
