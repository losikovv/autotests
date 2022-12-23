package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScangoRetailerConfigSHP extends BaseObject {
    @JsonProperty("scango_retailer_config")
    private ScangoRetailerConfigV1 scangoRetailerConfig;
}