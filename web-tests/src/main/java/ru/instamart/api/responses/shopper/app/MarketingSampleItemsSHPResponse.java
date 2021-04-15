package ru.instamart.api.responses.shopper.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingSampleItemsSHPResponse extends BaseResponseObject {
    @JsonProperty(value = "marketing_sample_items")
    private List<Object> marketingSampleItems = null;
}
