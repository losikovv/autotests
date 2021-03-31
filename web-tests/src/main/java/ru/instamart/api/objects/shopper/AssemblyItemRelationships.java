package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemRelationships extends BaseObject {
    private Cancellation cancellation;
    private ItemReturn itemReturn;
}
