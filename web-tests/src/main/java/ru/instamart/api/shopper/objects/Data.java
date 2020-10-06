package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class Data extends BaseObject {

    private String id;
    private String type;
    private Attributes attributes;

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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
