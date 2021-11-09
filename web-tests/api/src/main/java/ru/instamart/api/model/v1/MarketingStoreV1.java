
package ru.instamart.api.model.v1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingStoreV1 extends BaseObject {

    @JsonProperty("city_id")
    private Integer cityId;
    private Integer id;
    private AddressV1 location;
    private String name;
    @JsonProperty("on_demand")
    private Boolean onDemand;
    private RetailerV1 retailer;
    @JsonProperty("store_zones")
    private List<StoreZoneV1> storeZones;
    private String uuid;
}
