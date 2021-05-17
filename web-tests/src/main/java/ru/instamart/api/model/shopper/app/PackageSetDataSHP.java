package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetDataSHP extends BaseObject {
    private String id;
    private String type;
    private PackageSetAttributesSHP attributes;
    private PackageSetRelationshipsSHP relationships;
}
