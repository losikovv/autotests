package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperRelationships extends BaseObject {
    private Vehicles vehicles;
    private Equipment equipment;
    private Uniforms uniforms;
    private Store store;
    private Roles roles;
}
