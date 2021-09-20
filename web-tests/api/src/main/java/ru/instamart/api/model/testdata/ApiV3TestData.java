package ru.instamart.api.model.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiV3TestData {
    private int statusCode;
    private String clientTokenName;
    private String clientToken;
    private String shipTotal;
    private String itemId;
    private String itemIdName;
    private int itemQuantity;
    private int itemPrice;
    private int itemDiscount;
    private int itemPromoTotal;








}
