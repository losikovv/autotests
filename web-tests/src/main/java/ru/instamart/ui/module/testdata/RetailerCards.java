package ru.instamart.ui.module.testdata;

import ru.instamart.ui.common.pagesdata.LoyaltiesData;

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
