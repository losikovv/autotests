package ru.instamart.application.lib;

import ru.instamart.application.models.ReplacementPolicyData;

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
                "Звонить клиенту, убирать при недозвоне",
                "Позвонить мне. Если не удастся, убрать товар");
    }

    public static ReplacementPolicyData replace() {
        return new ReplacementPolicyData (
                3,
                "Делать замены самостоятельно",
                "Не звонить. Замену выбирает сборщик");
    }

    public static ReplacementPolicyData remove() {
        return new ReplacementPolicyData (
                4,
                "Убирать товар из заказа",
                "Не звонить. Убрать отсутствующий товар");
    }
}
