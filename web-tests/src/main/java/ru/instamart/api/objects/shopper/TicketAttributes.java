package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketAttributes extends BaseObject {
    private String title;
    private String status;
    private String uuid;
    private Boolean synced;
    private Integer unreadCount;
}
