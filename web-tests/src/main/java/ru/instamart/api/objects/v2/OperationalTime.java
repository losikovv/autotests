package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalTime extends BaseObject {
    @JsonProperty(value = "week_day")
    private String weekDay;
    @JsonProperty(value = "starts_at")
    private String startsAt;
    @JsonProperty(value = "ends_at")
    private String endsAt;
}
