package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSkuV2Response extends BaseObject {
	@JsonProperty("products_sku")
	private List<String> productsSku;
}