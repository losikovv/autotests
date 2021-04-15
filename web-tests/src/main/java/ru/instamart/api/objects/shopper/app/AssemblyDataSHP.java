package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyDataSHP extends BaseObject {
    private String id;
    private String type;
    private AssemblyAttributesSHP attributes;
    private AssemblyRelationshipsSHP relationships;
}
