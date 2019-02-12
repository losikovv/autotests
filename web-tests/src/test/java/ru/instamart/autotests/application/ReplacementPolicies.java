package ru.instamart.autotests.application;

import ru.instamart.autotests.models.ReplacementPolicyData;

public class ReplacementPolicies {

    public static ReplacementPolicyData callAndReplace() {
        return new ReplacementPolicyData (
                1,
                "Звонить клиенту, заменять при недозвоне",
                "Позвонить мне. Если не удастся, выбирает сборщик"
        );
    }

    public static ReplacementPolicyData callAndRemove() {
        return new ReplacementPolicyData (
                2,
                "",
                "Позвонить мне. Если не удастся, убрать товар");
    }

    public static ReplacementPolicyData replace() {
        return new ReplacementPolicyData (
                3,
                "",
                "Не звонить. Замену выбирает сборщик");
    }

    public static ReplacementPolicyData remove() {
        return new ReplacementPolicyData (
                4,
                "",
                "Не звонить. Убрать отсутствующий товар");
    }
}
