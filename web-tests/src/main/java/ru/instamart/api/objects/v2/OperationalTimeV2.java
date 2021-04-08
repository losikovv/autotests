package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalTimeV2 extends BaseObject {
    @JsonProperty(value = "week_day")
    private String weekDay;
    @JsonProperty(value = "starts_at")
    private String startsAt;
    @JsonProperty(value = "ends_at")
    private String endsAt;
}
