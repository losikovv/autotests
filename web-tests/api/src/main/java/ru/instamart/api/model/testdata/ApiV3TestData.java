package ru.instamart.api.model.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiV3TestData {
    //private Integer statusCode;
    private String clientTokenName;
    private String clientToken;
    private String retailer_id;
    private String storeId;
    private String shipTotal;
    private String itemId;
    private String itemIdName;
    private Integer itemQuantity;
    private Integer itemPrice;
    private Integer itemDiscount;
    private Integer itemPromoTotal;
}
