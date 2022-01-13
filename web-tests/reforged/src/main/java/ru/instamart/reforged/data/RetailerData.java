package ru.instamart.reforged.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Класс с данными ритейлера (инфа, относящаяся к магазину)
 */
@Data
@Builder
public final class RetailerData {

    private String name;
    private String itemsCount;
    private String totalWeight;
    private String totalAmount;
    private String deliveryInfo;
    private String alertInfo;
    private List<ItemData> itemsData;
}
