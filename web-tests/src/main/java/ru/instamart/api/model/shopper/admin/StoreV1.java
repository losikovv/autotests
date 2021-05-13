package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV1 extends BaseObject {
    private Integer id;
    private String name;
    private String uuid;
    private Integer operationalZoneId;
    private Integer retailerId;
    private Boolean active;
    private Boolean hasConveyor;
    private Boolean fastPayment;
    private Boolean fastPaymentCashless;
    private String fastPaymentMetroStoreDns;
    private Boolean expressDelivery;
    private Integer secondsForAssemblyItem;
    private Integer additionalSecondsForAssembly;
    private Boolean autoRouting;
    private Boolean boxScanning;
    private Integer deliveryAreaId;
    private String scheduleType;
    private String scheduleTypeHumanName;
    private LocationV1 location;
    private String fastPaymentMetroBarcodeMasked;
}
