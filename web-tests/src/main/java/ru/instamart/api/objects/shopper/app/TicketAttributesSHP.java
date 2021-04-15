package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketAttributesSHP extends BaseObject {
    private String title;
    private String status;
    private String uuid;
    private Boolean synced;
    private Integer unreadCount;
}
