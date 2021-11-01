
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MarketingSampleV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingSamplesV1Response extends BaseResponseObject {

    @JsonProperty("marketing_samples")
    private List<MarketingSampleV1> marketingSamples;
}
