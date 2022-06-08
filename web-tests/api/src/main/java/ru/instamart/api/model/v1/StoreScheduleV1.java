
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class StoreScheduleV1 extends BaseObject {

    private Long id;

    @JsonProperty("store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    private TemplateV1 template;
}
