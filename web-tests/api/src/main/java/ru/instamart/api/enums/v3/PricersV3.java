package ru.instamart.api.enums.v3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum PricersV3 {

    PER_ITEM("Pricer::PerItem"),
    PER_KILO("Pricer::PerKilo"),
    PER_PACKAGE("Pricer::PerPackage"),
    PER_PACK("Pricer::PerPack");

    private final String value;
}
