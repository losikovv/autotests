package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PhoneTokenV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "code_length")
    private Integer codeLength;
}
