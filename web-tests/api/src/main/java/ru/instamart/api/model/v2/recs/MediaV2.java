package ru.instamart.api.model.v2.recs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class MediaV2 extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("original_price")
    private String originalPrice;


    @JsonSchema(required = true)
    private List<ImagesItemV2> images;

    @JsonProperty("discount_ends_at")
    private String discountEndsAt;

    @JsonSchema(required = true)
    @JsonProperty("original_unit_price")
    private String originalUnitPrice;


    @JsonSchema(required = true)
    @JsonProperty("human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    @JsonProperty("unit_price")
    private String unitPrice;

    @JsonSchema(required = true)
    @JsonProperty("grams_per_unit")
    private Integer gramsPerUnit;

    @JsonSchema(required = true)
    private String price;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("price_type")
    private String priceType;

    @JsonSchema(required = true)
    @JsonProperty("items_per_pack")
    private Integer itemsPerPack;

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String sku;

    @JsonSchema(required = true)
    private String permalink;
}