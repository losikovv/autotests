package ru.instamart.api.enums.shadowcat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SimpleCondition {
    FIRST_ORDER("first_order", "Промокод действует только на первый заказ, а вы его уже сделали"),
    SBER_CUSTOMER("sber_loyalty", "Чтобы применить промокод, войдите по СберID и попробуйте снова"),
    FIRST_NTH_ORDER("first_nth_order", "Промокод действует только на первые несколько заказов"),
    B2B_ORDER("is_b2b_order", "Промокод действует только на заказы юридических лиц"),
    NOT_FIRST_ORDER("not_first_order", "Промокод действует только на повторных заказах, а у вас их еще не было"),
    ORDER_FROM_DATE("nth_order", "Промокод действует только на второй заказ"),
    ORDER_PRICE("order_price", "Промокод работает с заказами от 3200 ₽. Наберите еще немного");
    // TODO: 23.11.2022 add order_limit, order_series

    private final String route;

    private final String errorMessage;

}
