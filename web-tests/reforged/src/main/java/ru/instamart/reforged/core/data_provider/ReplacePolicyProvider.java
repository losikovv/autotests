package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;

public class ReplacePolicyProvider {

    @DataProvider(name = "replacementPolicy")
    public static Object[][] getReplacementPolicyName() {
        return new Object[][]{
                {"Позвонить мне. Подобрать замену, если не смогу ответить"},
                {"Позвонить мне. Убрать из заказа, если не смогу ответить"},
                {"Не звонить мне. Подобрать замену"},
                {"Не звонить мне. Убрать из заказа"}
        };
    }
}
