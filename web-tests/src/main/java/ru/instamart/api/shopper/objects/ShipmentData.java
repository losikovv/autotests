package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class ShipmentData extends BaseObject {

    private String id;
    private String type;
    private ShipmentAttributes attributes;
    private ShipmentRelationships relationships;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ShipmentAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ShipmentAttributes attributes) {
        this.attributes = attributes;
    }

    public ShipmentRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ShipmentRelationships relationships) {
        this.relationships = relationships;
    }

}
