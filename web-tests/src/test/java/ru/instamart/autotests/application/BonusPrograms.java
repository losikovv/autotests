package ru.instamart.autotests.application;

import ru.instamart.autotests.models.BonusProgramData;

public class BonusPrograms {

    public static BonusProgramData mnogoru() {
        return new BonusProgramData("Много.Ру","11600350",1);
    }

    public static BonusProgramData aeroflot() {
        return new BonusProgramData("Аэрофлот-Бонус","71891831",2);
    }
}
