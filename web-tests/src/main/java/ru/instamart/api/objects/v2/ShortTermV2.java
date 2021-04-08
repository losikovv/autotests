package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShortTermV2 extends BaseObject {
    private String emoji;
    @JsonProperty(value = "icon_url")
    private String iconUrl;
    private String html;
}
