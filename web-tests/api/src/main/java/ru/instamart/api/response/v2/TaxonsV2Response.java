package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonsV2Response extends BaseResponseObject {
    private List<TaxonV2> taxons = null;
    @JsonProperty(value = "promoted_taxons")
    private List<TaxonV2> promotedTaxons = null;
}
