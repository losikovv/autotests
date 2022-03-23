package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetaV3 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "current_page")
    private Integer currentPage;

    @JsonProperty(value = "next_page")
    private Integer nextPage;

    @JsonProperty(value = "previous_page")
    private Integer previousPage;

    @JsonSchema(required = true)
    @JsonProperty(value = "total_pages")
    private Integer totalPages;

    @JsonSchema(required = true)
    @JsonProperty(value = "per_page")
    private Integer perPage;

    @JsonSchema(required = true)
    @JsonProperty(value = "total_count")
    private Integer totalCount;
}
