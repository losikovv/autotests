package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UnscheduledShipmentV1 extends BaseObject {
    private Integer id;
    private Integer scheduleId;
    private Integer shipmentId;
    private String reason;
}
