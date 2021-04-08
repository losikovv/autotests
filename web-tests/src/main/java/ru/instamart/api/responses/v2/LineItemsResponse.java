package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.LineItem;
import ru.instamart.api.objects.v2.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemsResponse extends BaseResponseObject {
    @JsonProperty(value = "line_items")
    private List<LineItem> lineItems = null;
    private Meta meta;
}
