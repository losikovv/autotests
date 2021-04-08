package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.LineItemV2;
import ru.instamart.api.objects.v2.MetaV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemsV2Response extends BaseResponseObject {
    @JsonProperty(value = "line_items")
    private List<LineItemV2> lineItems = null;
    private MetaV2 meta;
}
