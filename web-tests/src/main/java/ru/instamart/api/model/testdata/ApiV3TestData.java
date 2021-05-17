package ru.instamart.api.model.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiV3TestData {
    private String itemId;
    private  int statusCode;
    private String itemIdName;
    private String clientToken;
    private String clientTokenName;
    private String shipTotal;
    private int itemQuantity;
    private int itemPrice;
    private int itemDiscount;
}
