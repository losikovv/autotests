
package ru.instamart.api.response.v1.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ImportsFileV1;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageMetasFilesV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("page_metas_files")
    private List<ImportsFileV1> pageMetasFiles;

    @JsonSchema(required = true)
    private MetaV1 meta;
}
