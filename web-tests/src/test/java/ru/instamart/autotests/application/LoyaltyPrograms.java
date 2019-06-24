package ru.instamart.autotests.application;

import ru.instamart.autotests.models.LoyaltyProgramData;

public class LoyaltyPrograms {

    public static LoyaltyProgramData metro() {
        return new LoyaltyProgramData("Метро","6430102700158901011764",3);
    }

    public static LoyaltyProgramData vkusvill() {
        return new LoyaltyProgramData("Вкусвилл","2281023",3);
    }
}
