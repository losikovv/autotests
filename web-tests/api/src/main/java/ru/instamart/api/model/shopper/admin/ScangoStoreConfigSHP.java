package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScangoStoreConfigSHP extends BaseObject {
	@JsonProperty("scango_store_config")
	private ScangoStoreConfigV1 scangoStoreConfig;
}