
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FaqGroupV1 extends BaseObject {

    @JsonSchema(required = true)
    private List<FaqV1> faqs;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Integer position;
}
