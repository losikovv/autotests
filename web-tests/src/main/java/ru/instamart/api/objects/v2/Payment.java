package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Payment extends BaseObject {
    private String number;
    private String state;
    @JsonProperty(value = "is_finalization_needed")
    private Boolean isFinalizationNeeded;
    @JsonProperty(value = "finalization_url")
    private Object finalizationUrl;
}
