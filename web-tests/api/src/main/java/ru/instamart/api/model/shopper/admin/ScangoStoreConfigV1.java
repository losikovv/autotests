package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScangoStoreConfigV1 extends BaseObject {
	@JsonProperty("alcohol_enabled")
	private Boolean alcoholEnabled;
	@JsonProperty("external_store_id")
	private String externalStoreId;
	private Integer id;
	private Boolean enabled;
}