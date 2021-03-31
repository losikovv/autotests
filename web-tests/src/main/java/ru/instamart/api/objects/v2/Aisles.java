package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class Aisles extends BaseObject {
    private int id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private int productCount;
    private List<Product> products;
}
