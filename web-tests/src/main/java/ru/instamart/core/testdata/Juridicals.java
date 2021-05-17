package ru.instamart.core.testdata;

import ru.instamart.ui.data.pagesdata.JuridicalData;

public class Juridicals {

    public static JuridicalData testJuridical() {

        return new JuridicalData(
                "ООО \"Автотест\"",
                "ул. Тестовская, 88",
                "1111111111111",
                "222222222",
                "33333333333333333333",
                "444444444",
                "Банк Тестовый",
                "55555555555555555555"
        );
    }
}
