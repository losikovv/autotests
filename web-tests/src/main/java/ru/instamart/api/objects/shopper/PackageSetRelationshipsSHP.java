package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetRelationshipsSHP extends BaseObject {
    private AssemblySHP assembly;
}
