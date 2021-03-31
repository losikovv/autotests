package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Taxon;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonResponse extends BaseResponseObject {
    private Taxon taxon;
}
