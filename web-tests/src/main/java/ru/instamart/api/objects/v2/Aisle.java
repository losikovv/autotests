package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Aisle extends BaseObject {
    private Integer id;
    @JsonProperty(value = "taxon_id")
    private Integer taxonId;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    private Icon icon;
}
