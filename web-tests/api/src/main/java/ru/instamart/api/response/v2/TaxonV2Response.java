package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private TaxonV2 taxon;
}
