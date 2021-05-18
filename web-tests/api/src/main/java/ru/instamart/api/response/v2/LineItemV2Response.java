package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV2Response extends BaseResponseObject {
    @JsonProperty(value = "line_item")
    private LineItemV2 lineItem;
}