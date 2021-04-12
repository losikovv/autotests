package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV3 extends BaseObject {
    @JsonProperty("starts_at")
    private String startsAt;
    @JsonProperty("ends_at")
    private String endsAt;
}
