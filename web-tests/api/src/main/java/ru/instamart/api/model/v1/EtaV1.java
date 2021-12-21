
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EtaV1 extends BaseObject {

    @JsonSchema(required = true)
    private Integer courierSpeed;
    @JsonSchema(required = true)
    private Integer deliveryTimeSigma;
    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private Integer window;
}
