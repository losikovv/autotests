package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer quantity;

    @JsonSchema(required = true)
    private Integer packs;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty(value = "promo_total")
    private Double promoTotal;

    @JsonSchema(required = true)
    private Double discount;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "customer_comment")
    private String customerComment;

    @JsonSchema(required = true)
    @JsonProperty(value = "product_in_stock")
    private Boolean productInStock;

    @JsonSchema(required = true)
    @JsonProperty(value = "total_diff")
    private Double totalDiff;

    @JsonSchema(ignore = true)
    private ProductV2 product;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_price")
    private Double unitPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_quantity")
    private Double unitQuantity;

    @JsonSchema(required = true)
    private String uuid;

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
