package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.v1.LineItem;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemsResponse extends BaseResponseObject {
    @JsonProperty(value = "line_items")
    private List<LineItem> lineItems = null;
}
