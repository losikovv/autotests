package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class SessionData extends BaseObject {

    private String id;
    private String type;
    private SessionAttributes attributes;

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

    public SessionAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(SessionAttributes attributes) {
        this.attributes = attributes;
    }

}
