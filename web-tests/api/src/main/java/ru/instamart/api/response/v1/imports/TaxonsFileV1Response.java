
package ru.instamart.api.response.v1.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ImportsFileV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonsFileV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("taxons_file")
    private ImportsFileV1 taxonsFile;
}
