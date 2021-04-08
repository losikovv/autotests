package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyRelationships extends BaseObject {
    private Shipment shipment;
    private Items items;
    private PackageSets packageSets;
}
