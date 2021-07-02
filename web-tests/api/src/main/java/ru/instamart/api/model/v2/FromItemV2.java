package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class FromItemV2 extends BaseObject {
    @JsonProperty("product_in_stock")
    private boolean productInStock;
    private double total;
    private ProductV2 product;
    private int quantity;
    private double price;
    private int id;
    @JsonProperty("total_diff")
    private double totalDiff;
    @JsonProperty("customer_comment")
    private String customerComment;
}