package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Тип внешней сборки
 */
@RequiredArgsConstructor
@ToString
@Getter
public enum ExternalAssemblyKind {
    /**
     * Шоппер
     */
    SHOPPER("shopper"),
    /**
     * Внешняя система
     */
    EXTERNAL("external"),
    /**
     * Шоппер + Внешняя система
     */
    BOTH("both"),
    /**
     * Сборка ритейлера, доставка Сбермаркета
     */
    DELIVERY_BY_SBERMARKET("delivery_by_sbermarket"),
    /**
     * Интеграция для учёта
     */
    INTEGRATION_FOR_ACCOUNTING("integration_for_accounting"),
    /**
     * Сборка и доставка ритейлером
     */
    DELIVERY_BY_RETAILER("delivery_by_retailer");

    private final String value;
}
