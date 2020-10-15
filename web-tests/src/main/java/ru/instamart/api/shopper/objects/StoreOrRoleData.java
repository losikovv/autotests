package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class StoreOrRoleData extends BaseObject {

    private String id;
    private String type;
    private StoreOrRoleAttributes attributes;

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

    public StoreOrRoleAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(StoreOrRoleAttributes attributes) {
        this.attributes = attributes;
    }

}
