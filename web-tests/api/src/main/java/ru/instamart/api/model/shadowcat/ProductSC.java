package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductSC extends BaseObject {

    @JsonProperty("category_ids")
    private List<String> categoryIds;

    @JsonProperty("category_slug")
    private String categorySlug;

    private String id;

    @JsonProperty("discount_price")
    private double discountPrice;

    private double price;

    private int quantity;

    private String sku;
}
