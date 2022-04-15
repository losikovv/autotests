
package ru.instamart.api.response.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.workflows.Segment;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignmentResponse extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("performer_uuid")
    private String performerUuid;

    @JsonSchema(required = true)
    private Segment segment;

    @JsonSchema(required = true)
    private Long status;
}
