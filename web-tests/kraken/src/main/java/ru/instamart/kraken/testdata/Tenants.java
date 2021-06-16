package ru.instamart.kraken.testdata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum Tenants {
    SBERMARKET("СберМАРКЕТ", "sbermarket"),
    METRO("METRO Delivery CC", "metro"),
    LENTA("ЛЕНТА", "lenta"),
    OKEY("Гипермаркет ОКЕЙ", "okey"),
    SELGROS("SELGROS Cash&Carry", "selgros");

    private final String title;
    private final String alias;
}