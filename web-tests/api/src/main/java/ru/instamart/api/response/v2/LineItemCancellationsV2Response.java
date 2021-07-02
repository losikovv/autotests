package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.LineItemCancellationsV2;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemCancellationsV2Response extends BaseResponseObject {
    @JsonProperty(value = "line_item_cancellations")
    private List<LineItemCancellationsV2> lineItemCancellations = null;
    private MetaV2 meta;
}
