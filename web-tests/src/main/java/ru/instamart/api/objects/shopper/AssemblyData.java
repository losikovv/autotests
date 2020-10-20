package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class AssemblyData extends BaseObject {

    private String id;
    private String type;
    private AssemblyAttributes attributes;
    private AssemblyRelationships relationships;

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

    public AssemblyAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AssemblyAttributes attributes) {
        this.attributes = attributes;
    }

    public AssemblyRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(AssemblyRelationships relationships) {
        this.relationships = relationships;
    }

}
