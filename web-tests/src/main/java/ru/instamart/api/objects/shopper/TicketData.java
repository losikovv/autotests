package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketData extends BaseObject {
    private String id;
    private String type;
    private TicketAttributes attributes;
    private TicketRelationships relationships;
}