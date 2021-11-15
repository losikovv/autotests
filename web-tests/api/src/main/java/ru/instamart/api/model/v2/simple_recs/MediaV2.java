package ru.instamart.api.model.v2.simple_recs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class MediaV2 extends BaseResponseObject {
	@JsonProperty("original_price")
	private String originalPrice;
	private List<ImagesItemV2> images;
	@JsonProperty("volume_type")
	private String volumeType;
	private String discount;
	@JsonProperty("original_unit_price")
	private String originalUnitPrice;
	@JsonProperty("human_volume")
	private String humanVolume;
	@JsonProperty("unit_price")
	private String unitPrice;
	private String volume;
	@JsonProperty("grams_per_unit")
	private int gramsPerUnit;
	private String price;
	private String name;
	@JsonProperty("price_type")
	private String priceType;
	@JsonProperty("items_per_pack")
	private int itemsPerPack;
	@JsonProperty("retailer_sku")
	private String retailerSku;
	private int id;
	private String sku;
	private String permalink;
	@JsonProperty("discount_ends_at")
	private String discountEndsAt;
}