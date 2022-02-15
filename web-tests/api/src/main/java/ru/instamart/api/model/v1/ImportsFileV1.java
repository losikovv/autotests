
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImportsFileV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("logfile_url")
    private String logfileUrl;

    @JsonSchema(required = true)
    @JsonProperty("payload_file_name")
    private String payloadFileName;

    @JsonSchema(required = true)
    @JsonProperty("payload_file_url")
    private String payloadFileUrl;

    @Null
    @JsonSchema(required = true)
    private String status;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;

    @Null
    @JsonSchema(required = true)
    private UserV1 user;
}
