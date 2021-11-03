package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ReplacementPoliciesV2 {
    REPLACEMENT_1(1, "Позвонить мне. Подобрать замену, если не смогу ответить"),
    REPLACEMENT_2(2, "Позвонить мне. Убрать из заказа, если не смогу ответить"),
    REPLACEMENT_3(3, "Не звонить мне. Подобрать замену"),
    REPLACEMENT_4(4, "Не звонить мне. Убрать из заказа");

    private final Integer id;
    private final String description;
}
