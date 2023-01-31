package ru.instamart.api.response.v1.gw.assembly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FreePickersV1Response {
    @JsonProperty("full_name")
    private String fullName;
    private String id;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("shp_id")
    private int shpId;
    @JsonProperty("store_id")
    private String storeId;
}
