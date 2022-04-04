
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmailsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("b2b_email")
    private String b2bEmail;

    @JsonSchema(required = true)
    @JsonProperty("content_email")
    private String contentEmail;

    @JsonSchema(required = true)
    @JsonProperty("feedback_email")
    private String feedbackEmail;

    @JsonSchema(required = true)
    @JsonProperty("orders_email")
    private String ordersEmail;

    @JsonSchema(required = true)
    @JsonProperty("press_email")
    private String pressEmail;
}
