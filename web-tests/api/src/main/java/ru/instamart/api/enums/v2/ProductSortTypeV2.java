package ru.instamart.api.enums.v2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ProductSortTypeV2 {
    POPULARITY("popularity", "По популярности", "desc"),
    PRICE_ASC("price_asc", "Сначала дешевые", "asc"),
    PRICE_DESC("price_desc", "Сначала дорогие", "desc"),
    UNIT_PRICE_ASC("unit_price_asc", "Выгоднее по весу", "asc");

    private final String key;
    private final String name;
    private final String order;
}
