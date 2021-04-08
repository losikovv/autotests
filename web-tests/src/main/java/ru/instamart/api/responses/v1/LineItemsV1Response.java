package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v1.LineItemV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemsV1Response extends BaseResponseObject {
    @JsonProperty(value = "line_items")
    private List<LineItemV1> lineItems = null;
}
