package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class AppVersionData extends BaseObject {

    private String id;
    private String type;
    private AppVersionAttributes attributes;

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

    public AppVersionAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AppVersionAttributes attributes) {
        this.attributes = attributes;
    }

}
