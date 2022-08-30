package ru.instamart.reforged.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuickFilters {
    UNPAID("Неоплаченные"),
    NOT_ASSIGNED("Без назначений"),
    LONG_DISPATCH("Долгая диспетчеризация"),

    QUICK_DELIVERY("Быстрая доставка"),
    COMPLETED("Завершённые"),
    NOT_COMPLETED("Незавершённые"),
    B2B_CLIENTS("B2B клиенты"),

    DELIVERY_TIME_CHANGED("Время доставки перенесено");
    private final String name;
}
