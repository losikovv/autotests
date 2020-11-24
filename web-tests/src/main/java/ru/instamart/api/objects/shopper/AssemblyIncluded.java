package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class AssemblyIncluded extends BaseObject {
    private String id;
    private String type;
    private AssemblyIncludedAttributes attributes;
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

    public AssemblyIncludedAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AssemblyIncludedAttributes attributes) {
        this.attributes = attributes;
    }

    public ShipmentRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ShipmentRelationships relationships) {
        this.relationships = relationships;
    }
}
