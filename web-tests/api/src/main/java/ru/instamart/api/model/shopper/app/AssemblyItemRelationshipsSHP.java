package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemRelationshipsSHP extends BaseObject {
    private CancellationSHP cancellation;
    private ItemReturnSHP itemReturn;
}
