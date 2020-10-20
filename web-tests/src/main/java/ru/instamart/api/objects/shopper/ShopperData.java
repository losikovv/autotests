package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ShopperData extends BaseObject {

    private String id;
    private String type;
    private ShopperAttributes attributes;
    private ShopperRelationships relationships;

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

    public ShopperAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ShopperAttributes attributes) {
        this.attributes = attributes;
    }

    public ShopperRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(ShopperRelationships relationships) {
        this.relationships = relationships;
    }

}
