package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentDataSHP extends BaseObject {
    private String id;
    private String type;
    private ShipmentAttributesSHP attributes;
    private ShipmentRelationshipsSHP relationships;
}
