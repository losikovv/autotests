package ru.instamart.api.enums.shadowcat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.instamart.api.enums.v2.ShippingMethodV2.PICKUP;

@AllArgsConstructor
@Getter
public enum TwoStepCondition {
    DELIVERY_METHOD("delivery_methods","Промокод действует только при определенном способе доставки", PICKUP.getMethod()),
    CUSTOMER("customers","Промокод действует только для определенных пользователей", "693f9bcc-b899-4e2f-afaa-53de62b20e66"),
    PAYMENT("payment_methods","Промокод действует только при определенном способе оплаты", "Spree::Gateway::SberGateway"),
    PRODUCT("products","Промокод работает только при покупке определенных товаров", "15627"),
    REGION("regions","Промокод не действует в вашем регионе", "1"),
    RETAILER("retailers","Промокод не действует для заказов из этого ретейлера", "29f2a374-ac67-42bd-8321-81cbbbe77061"),
    STORE_FRONT("store_fronts","Промокод не действует для заказов с этого сайта", "sbermarket"),
    SUBSCRIPTION("subscriptions","Промокод действует только для подписчиков SberPrime", "prime_basic");

    private final String route;

    private final String errorMessage;

    private final String conditionValue;
}
