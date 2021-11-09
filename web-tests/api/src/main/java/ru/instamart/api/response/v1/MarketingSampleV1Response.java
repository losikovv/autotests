package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MarketingSampleV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingSampleV1Response extends BaseResponseObject {

    @JsonProperty("marketing_sample")
    private MarketingSampleV1 marketingSample;
}
