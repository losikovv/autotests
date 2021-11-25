package ru.instamart.api.model.v2.recs;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecV2 extends BaseResponseObject {
    @JsonSchema(required = true)
    private ExtV2 ext;
    @JsonSchema(required = true)
    private MediaV2 media;
    private String placementId;
}