package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV2 extends BaseObject {
    private Integer id;
    private Integer quantity;
    private Integer packs;
    private Double price;
    private Double total;
    @JsonProperty(value = "promo_total")
    private Double promoTotal;
    private Double discount;
    @JsonProperty(value = "customer_comment")
    private Object customerComment;
    @JsonProperty(value = "product_in_stock")
    private Boolean productInStock;
    @JsonProperty(value = "total_diff")
    private Double totalDiff;
    private ProductV2 product;
    @JsonProperty(value = "unit_price")
    private Double unitPrice;
    @JsonProperty(value = "unit_quantity")
    private Integer unitQuantity;

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "В корзину добавлен товар:\n",
                "\n")
                .add(getProduct().getName())
                .add("id: " + getProduct().getId())
                .add("quantity: " + quantity)
                .add("total: " + total)
                .toString();
    }
}
