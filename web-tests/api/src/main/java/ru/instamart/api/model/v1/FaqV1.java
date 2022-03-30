
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class FaqV1 extends BaseObject {

    @JsonSchema(required = true)
    private String answer;

    @JsonSchema(required = true)
    @JsonProperty("faq_group_id")
    private Long faqGroupId;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private String question;
}
