
package ru.instamart.api.model.v1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TemplateV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("delivery_times")
    private List<DeliveryTimeV1> deliveryTimes;
}
