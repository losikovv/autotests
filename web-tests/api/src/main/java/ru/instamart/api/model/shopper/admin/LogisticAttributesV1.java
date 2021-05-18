package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LogisticAttributesV1 extends BaseObject {
    private Boolean highPriority;
    private String specifiedDeliveryBefore;
    private String specifiedDeliveryAfter;
    private Boolean doNotRoute;
}
