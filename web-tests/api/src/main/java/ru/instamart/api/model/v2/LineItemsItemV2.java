package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class LineItemsItemV2 extends BaseObject {
	@JsonProperty("promo_total")
	private Double promoTotal;
	private ProductV2 product;
	private Integer quantity;
	private Double discount;
	@JsonProperty("unit_price")
	private Double unitPrice;
	private Integer packs;
	@JsonProperty("total_diff")
	private Double totalDiff;
	private String uuid;
	@JsonProperty("product_in_stock")
	private Boolean productInStock;
	private Double total;
	private Double price;
	private Integer id;
	@JsonProperty("customer_comment")
	private Object customerComment;
	@JsonProperty("unit_quantity")
	private Double unitQuantity;
}