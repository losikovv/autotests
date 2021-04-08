package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentRelationshipsSHP extends BaseObject {
    private AssemblySHP assembly;
    private AssemblyCheckListSHP assemblyCheckList;
    private TicketsSHP tickets;
    private LogisticAttributesSHP logisticAttributes;
}
