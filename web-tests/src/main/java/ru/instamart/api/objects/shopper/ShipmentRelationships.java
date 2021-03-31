package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentRelationships extends BaseObject {
    private Assembly assembly;
    private AssemblyCheckList assemblyCheckList;
    private Tickets tickets;
    private LogisticAttributes logisticAttributes;
}
