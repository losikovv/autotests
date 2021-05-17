package ru.instamart.core.testdata;

import ru.instamart.core.testdata.pagesdata.LoyaltiesData;

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
