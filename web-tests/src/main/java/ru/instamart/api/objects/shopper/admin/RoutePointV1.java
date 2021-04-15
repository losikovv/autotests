package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoutePointV1 extends BaseObject {
    private Integer id;
    private Object externalNumber;
    private Integer position;
    private Integer shipmentId;
    private String planArrivalAt;
    private String factArrivalAt;
    private String state;
    private String humanState;
    private String number;
    private LogisticAttributesV1 logisticAttributes;
}
