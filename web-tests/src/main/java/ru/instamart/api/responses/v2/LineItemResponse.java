package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.LineItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemResponse extends BaseResponseObject {
    @JsonProperty(value = "line_item")
    private LineItem lineItem;
}