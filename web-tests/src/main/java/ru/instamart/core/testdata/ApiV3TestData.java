package ru.instamart.core.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.instamart.api.requests.v2.ShipmentsV2Request;
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
