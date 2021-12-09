package ru.instamart.api.rails_response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class Offer extends BaseObject {

	@JsonProperty("store_id")
	private Integer storeId;

	@JsonProperty("display_price_affix")
	private String displayPriceAffix;

	@JsonProperty("retailer_id")
	private Integer retailerId;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("published")
	private Boolean published;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("deleted_at")
	private Object deletedAt;

	@JsonProperty("quantity_initial")
	private Double quantityInitial;

	@JsonProperty("quantity_affix")
	private String quantityAffix;

	@JsonProperty("product_sku")
	private String productSku;

	@JsonProperty("pickup_order")
	private Integer pickupOrder;

	@JsonProperty("grams_per_unit")
	private Object gramsPerUnit;

	@JsonProperty("max_stock")
	private Integer maxStock;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("quantity_increment")
	private Double quantityIncrement;

	@JsonProperty("product_id")
	private Object productId;

	@JsonProperty("quantity_type")
	private String quantityType;

	@JsonProperty("name")
	private String name;

	@JsonProperty("retailer_sku")
	private String retailerSku;

	@JsonProperty("items_per_pack")
	private Integer itemsPerPack;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("stock")
	private Integer stock;

	@JsonProperty("rsku")
	private Object rsku;

	@JsonProperty("vat_rate")
	private Integer vatRate;
}