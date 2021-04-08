package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.LineItemV2;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV2Response extends BaseResponseObject {
    @JsonProperty(value = "line_item")
    private LineItemV2 lineItem;
}