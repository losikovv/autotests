package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV1 extends BaseObject {
    private Integer id;
    private String number;
    private Integer weight;
    private Object logisticVolume;
    private String state;
    private String dispatcherComment;
    private Integer itemCount;
    private Boolean isOnlinePayment;
    private Object tenantId;
    private String retailer;
    private String uuid;
    private String email;
    private String lifePayPaymentMethod;
    private Boolean isFirstOrder;
    private Boolean closingDocsRequired;
    private Boolean isOutdated;
    private List<ShipmentV1> linkedShipments = null;
    private String deliveryBefore;
    private Object inspectionResult;
    private Integer storeId;
    private String total;
    private String cost;
    private String itemTotal;
    private String adjustmentTotal;
    private String deliveryDate;
    private String deliveryInterval;
    private String customerComment;
    private String orderDispatcherComment;
    private LocationV1 address;
    private String retailerName;
    private String assemblyState;
    private Boolean totalGreaterThanReceipt;
    private PaymentV1 payment;
    private ReplacementPolicyV1 replacementPolicy;
    private Boolean isPaid;
    private Boolean canBePrinted;
    private Boolean canChangePaymentTool;
    private String storeImportKey;
    private Object retailerCard;
    private String assemblyDeadlineStatus;
    private String assemblyDeadlineAt;
    private Boolean isPickup;
    private Boolean isAlcohol;
    private Boolean isTerminalState;
}
