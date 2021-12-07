package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetaV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer currentPage;

    @Null
    @JsonSchema(required = true)
    private Integer nextPage;

    @Null
    @JsonSchema(required = true)
    private Integer previousPage;

    @JsonSchema(required = true)
    private Integer totalPages;

    @JsonSchema(required = true)
    private Integer perPage;

    @JsonSchema(required = true)
    private Integer totalCount;
}
