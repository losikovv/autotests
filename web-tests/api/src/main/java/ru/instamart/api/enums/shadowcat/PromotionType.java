package ru.instamart.api.enums.shadowcat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PromotionType {
    DISCOUNT(3, "krakendisc", "discount",1000, 100),
    ASSEMBLY_DISCOUNT(16, "krakenassembly", "delivery_assembly", 100, 10),
    DELIVERY_DISCOUNT(17, "krakendel", "delivery_discount", 100, 10),
    SURGE_DISCOUNT(15, "krakensurge", "delivery_surge", 100, 10),
    SBERSPASIBO(1299, "krakenspas", "cashback_sberspasibo", 500, 100);

    private final int id;

    private final String code;

    private final String type;

    private final int value;

    private final int percent;
}
