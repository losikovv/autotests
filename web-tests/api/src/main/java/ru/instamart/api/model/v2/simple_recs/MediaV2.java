package ru.instamart.api.model.v2.simple_recs;

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

	@JsonSchema(required = true)
	@JsonProperty("volume_type")
	private String volumeType;

	@JsonSchema(required = true)
	private String discount;

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
	private String volume;

	@JsonSchema(required = true)
	@JsonProperty("grams_per_unit")
	private int gramsPerUnit;

	@JsonSchema(required = true)
	private String price;

	@JsonSchema(required = true)
	private String name;

	@JsonSchema(required = true)
	@JsonProperty("price_type")
	private String priceType;

	@JsonSchema(required = true)
	@JsonProperty("items_per_pack")
	private int itemsPerPack;

	@JsonSchema(required = true)
	@JsonProperty("retailer_sku")
	private String retailerSku;

	@JsonSchema(required = true)
	private Long id;

	@JsonSchema(required = true)
	private String sku;

	@JsonSchema(required = true)
	private String permalink;

	@JsonProperty("discount_ends_at")
	private String discountEndsAt;
}