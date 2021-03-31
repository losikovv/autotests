package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperIncludedAttributes extends BaseObject {
    /**
     * Base attributes
     */
    private String name;
    /**
     * Store attributes
     */
    private String uuid;
    private Integer operationalZoneId;
    private Integer retailerId;
    private Boolean active;
    private Boolean hasConveyor;
    private Boolean fastPayment;
    private Object fastPaymentMetroStoreDns;
    private Boolean expressDelivery;
    private Integer secondsForAssemblyItem;
    private Integer additionalSecondsForAssembly;
    private Boolean autoRouting;
    private Boolean boxScanning;
    private Object deliveryAreaId;
    private Address location;
    private String scheduleType;
    private String scheduleTypeHumanName;
    /**
     * Equipment attributes
     */
    private Integer shopperId;
    private String serial;
    private String suppliedAt;
    private Object kind;
    /**
     * Role attributes
     */
    private String humanName;
}
