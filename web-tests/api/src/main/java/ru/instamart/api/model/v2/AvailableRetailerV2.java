
package ru.instamart.api.model.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AvailableRetailerV2 extends BaseObject {

    @JsonSchema(required = true)
    private String retailerName;

    @JsonSchema(required = true)
    private List<Long> shipmentIds;
}
