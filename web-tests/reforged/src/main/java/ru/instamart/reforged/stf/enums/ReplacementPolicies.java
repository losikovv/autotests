package ru.instamart.reforged.stf.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReplacementPolicies {
    CALL_AND_REPLACE("Позвонить мне. Подобрать замену, если не смогу ответить"),
    CALL_AND_REMOVE("Позвонить мне. Убрать из заказа, если не смогу ответить"),
    DONT_CALL_AND_REPLACE("Не звонить мне. Подобрать замену"),
    DONT_CALL_AND_REMOVE("Не звонить мне. Убрать из заказа");

    private final String name;
}
