package ru.instamart.reforged.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatuses {
    PAID("Оплачен"),
    NOT_PAID("Не оплачен"),
    BALANCE_DUE("Недоплата"),
    OVERPAID("Переплата"),
    FAILED("Ошибка");
    private String name;
}
