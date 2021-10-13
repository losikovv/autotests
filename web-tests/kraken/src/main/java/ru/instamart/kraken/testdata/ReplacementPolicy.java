package ru.instamart.kraken.testdata;

import ru.instamart.kraken.testdata.pagesdata.ReplacementPolicyData;

public class ReplacementPolicy {

    public static ReplacementPolicyData callAndReplace() {
        return new ReplacementPolicyData(
                1,
                "Позвонить мне. Подобрать замену, если не смогу ответить",
                "Вариант замен звонить / заменить"
        );
    }

    public static ReplacementPolicyData callAndRemove() {
        return new ReplacementPolicyData(
                2,
                "Позвонить мне. Убрать из заказа, если не смогу ответить",
                "Вариант замен звонить / убрать"
        );
    }

    public static ReplacementPolicyData noCallAndReplace() {
        return new ReplacementPolicyData(
                3,
                "Не звонить мне. Подобрать замену",
                "Вариант замен не звонить / заменить"
        );
    }

    public static ReplacementPolicyData noCallAndRemove() {
        return new ReplacementPolicyData(
                4,
                "Не звонить мне. Убрать из заказа",
                "Вариант замен не звонить / убрать"
        );
    }
}
