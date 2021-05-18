package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SizeLabelV3 extends BaseObject {
    private String volume;
    @JsonProperty("volume_type")
    private String volumeType;
}
