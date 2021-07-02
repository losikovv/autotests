package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.LineItemReplacementsItem;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemReplacementsV2Response extends BaseResponseObject {
    @JsonProperty(value = "line_item_replacements")
    private List<LineItemReplacementsItem> lineItemReplacements = null;
}
