package ru.instamart.api.enums.shadowcat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SimpleCondition {
    FIRST_ORDER("first_order", "Промокод действует только на первый заказ, а вы его уже сделали", 0, 2),
    SBER_CUSTOMER("sber_loyalty", "Чтобы применить промокод, войдите по СберID и попробуйте снова", 1, 0),
    FIRST_NTH_ORDER("first_nth_order", "Промокод действует только на первые несколько заказов", 1 , 1001),
    B2B_ORDER("is_b2b_order", "Промокод действует только на заказы юридических лиц", 1, 0),
    NOT_FIRST_ORDER("not_first_order", "Промокод действует только на повторных заказах, а у вас их еще не было", 1, 0),
    ORDER_PRICE("order_price", "Промокод работает с заказами от 3200 ₽. Наберите еще немного" , 1100, 10);
    // TODO: 23.11.2022 add order_limit, order_series

    private final String route;

    private final String errorMessage;

    private final int conditionValuePass;

    private final int conditionValueFail;
}
