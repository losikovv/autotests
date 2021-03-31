package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ShipAddressChange;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipAddressChangeResponse extends BaseResponseObject {
    @JsonProperty(value = "ship_address_change")
    private ShipAddressChange shipAddressChange;
}
