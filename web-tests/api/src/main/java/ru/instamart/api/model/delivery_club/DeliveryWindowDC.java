package ru.instamart.api.model.delivery_club;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowDC extends BaseObject {
    @JsonSchema(required = true)
    private String startAt;
    @JsonSchema(required = true)
    private String endAt;
}
