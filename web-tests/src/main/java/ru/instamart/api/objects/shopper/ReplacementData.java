package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ReplacementData extends BaseObject {
    private String id;
    private String type;
    private ReplacementAttributes attributes;

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

    public ReplacementAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ReplacementAttributes attributes) {
        this.attributes = attributes;
    }

}
