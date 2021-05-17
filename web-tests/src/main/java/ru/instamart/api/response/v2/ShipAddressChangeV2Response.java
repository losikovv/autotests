package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ShipAddressChangeV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipAddressChangeV2Response extends BaseResponseObject {
    @JsonProperty(value = "ship_address_change")
    private ShipAddressChangeV2 shipAddressChange;
}
