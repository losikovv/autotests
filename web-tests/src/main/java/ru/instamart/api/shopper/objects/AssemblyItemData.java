package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class AssemblyItemData extends BaseObject {

    private String id;
    private String type;
    private AssemblyItemAttributes attributes;
    private AssemblyItemRelationShips relationships;

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

    public AssemblyItemAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AssemblyItemAttributes attributes) {
        this.attributes = attributes;
    }

    public AssemblyItemRelationShips getRelationships() {
        return relationships;
    }

    public void setRelationships(AssemblyItemRelationShips relationships) {
        this.relationships = relationships;
    }

}
