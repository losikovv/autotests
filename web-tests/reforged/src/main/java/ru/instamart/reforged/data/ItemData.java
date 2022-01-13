package ru.instamart.reforged.data;

import lombok.Builder;
import lombok.Data;

/**
 * Класс с данными товара (инфа, относящаяся к конкретному товару)
 */
@Data
@Builder
public final class ItemData {

    private String count;
    private String name;
    private String packageSize;
    private String price;
    private String totalAmount;
}
