package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonsV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<TaxonV2> taxons = null;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "promoted_taxons")
    private List<TaxonV2> promotedTaxons = null;
}
