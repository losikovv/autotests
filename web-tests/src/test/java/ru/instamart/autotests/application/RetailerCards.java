package ru.instamart.autotests.application;

import ru.instamart.autotests.models.LoyaltiesData;

public class RetailerCards {

    public static LoyaltiesData metro() {
        return new LoyaltiesData(
                "Метро",
                "6430102700158901011764",
                "карта клиента Metro Cash & Carry"
        );
    }

    public static LoyaltiesData vkusvill() {
        return new LoyaltiesData(
                "Вкусвилл",
                "2281023",
                "карта магазина Вкусвилл"
        );
    }
}
