package ru.instamart.api.responses.shopper;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingSampleItemsResponse extends BaseResponseObject {
    @JsonProperty(value = "marketing_sample_items")
    private List<Object> marketingSampleItems = null;
}
