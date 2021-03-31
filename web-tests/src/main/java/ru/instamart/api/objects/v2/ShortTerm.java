package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShortTerm extends BaseObject {
    private String emoji;
    @JsonProperty(value = "icon_url")
    private String iconUrl;
    private String html;
}
