package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class PackageSetData extends BaseObject {

    private String id;
    private String type;
    private PackageSetAttributes attributes;
    private PackageSetRelationships relationships;

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

    public PackageSetAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PackageSetAttributes attributes) {
        this.attributes = attributes;
    }

    public PackageSetRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(PackageSetRelationships relationships) {
        this.relationships = relationships;
    }

}
