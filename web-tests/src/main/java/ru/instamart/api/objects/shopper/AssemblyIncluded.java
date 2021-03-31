package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyIncluded extends BaseObject {
    private String id;
    private String type;
    private AssemblyIncludedAttributes attributes;
    private ShipmentRelationships relationships;
}
