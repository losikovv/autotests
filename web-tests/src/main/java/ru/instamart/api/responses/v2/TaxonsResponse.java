package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.v2.Taxon;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonsResponse extends BaseResponseObject {
    private List<Taxon> taxons = null;
    @JsonProperty(value = "promoted_taxons")
    private List<Taxon> promotedTaxons = null;
}
