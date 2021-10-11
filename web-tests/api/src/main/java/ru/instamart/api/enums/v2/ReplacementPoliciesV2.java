package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ReplacementPoliciesV2 {
    REPLACEMENT1(1, "Позвонить мне. Подобрать замену, если не смогу ответить"),
    REPLACEMENT2(2, "Позвонить мне. Убрать из заказа, если не смогу ответить"),
    REPLACEMENT3(3, "Не звонить мне. Подобрать замену"),
    REPLACEMENT4(4, "Не звонить мне. Убрать из заказа");

    private final Integer id;
    private final String description;
}
