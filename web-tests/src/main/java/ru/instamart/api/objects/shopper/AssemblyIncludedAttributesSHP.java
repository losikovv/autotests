package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyIncludedAttributesSHP extends BaseObject {
    /**
     * Base attributes
     */
    private String number;
    /**
     * Shipment attributes
     */
    private Integer weight;
    private String logisticVolume;
    private String state;
    private Object dispatcherComment;
    private Integer itemCount;
    private Boolean isOnlinePayment;
    private String tenantId;
    private String retailer;
    private String uuid;
    private String email;
    private String lifePayPaymentMethod;
    private Boolean isFirstOrder;
    private Boolean closingDocsRequired;
    private Boolean isOutdated;
    private List<Object> linkedShipments = null;
    private String deliveryBefore;
    private Object inspectionResult;
    private String total;
    private String cost;
    private String itemTotal;
    private String adjustmentTotal;
    private String deliveryDate;
    private String deliveryInterval;
    private String customerComment;
    private Object orderDispatcherComment;
    private AddressSHP address;
    private String retailerName;
    private String assemblyState;
    private Object totalGreaterThanReceipt;
    private PaymentSHP payment;
    private ReplacementPolicySHP replacementPolicy;
    private Boolean isPaid;
    private Object canBePrinted;
    private Boolean canChangePaymentTool;
    private String storeImportKey;
    private Object retailerCard;
    private String assemblyDeadlineStatus;
    private String assemblyDeadlineAt;
    private Boolean isPickup;
    private Boolean isAlcohol;
    private Boolean isTerminalState;
    /**
     * Package set attributes
     */
    private String location;
    private Object qty;
    private String boxNumber;
}

