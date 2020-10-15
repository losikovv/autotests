package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class TicketData extends BaseObject {

    private String id;
    private String type;
    private TicketAttributes attributes;
    private TicketRelationships relationships;

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

    public TicketAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(TicketAttributes attributes) {
        this.attributes = attributes;
    }

    public TicketRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(TicketRelationships relationships) {
        this.relationships = relationships;
    }

}