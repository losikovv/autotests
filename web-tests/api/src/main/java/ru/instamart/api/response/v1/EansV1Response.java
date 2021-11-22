package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.EanV1;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class EansV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private List<EanV1> eans;

    @JsonSchema(required = true)
    private MetaV1 meta;
}
