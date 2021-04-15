package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyRelationshipsSHP extends BaseObject {
    private ShipmentSHP shipment;
    private ItemsSHP items;
    private PackageSetsSHP packageSets;
}
