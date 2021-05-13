package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperRelationshipsSHP extends BaseObject {
    private VehiclesSHP vehicles;
    private EquipmentSHP equipment;
    private UniformsSHP uniforms;
    private StoreSHP store;
    private RolesSHP roles;
}
