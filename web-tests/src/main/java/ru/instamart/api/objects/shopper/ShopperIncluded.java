package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ShopperIncluded extends BaseObject {

    private String id;
    private String type;
    private ShopperIncludedAttributes attributes;

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

    public ShopperIncludedAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ShopperIncludedAttributes attributes) {
        this.attributes = attributes;
    }

}
