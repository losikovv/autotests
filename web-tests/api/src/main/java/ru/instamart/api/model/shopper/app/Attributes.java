package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Attributes extends BaseObject {
    private String logisticVolume;
    private Object inspectionResult;
    private Object canBePrinted;
    private String customerComment;
    private String storeImportKey;
    private boolean closingDocsRequired;
    private boolean isOutdated;
    private String deliveryInterval;
    private boolean isPickup;
    private String deliveryDeadlineAt;
    private String uuid;
    private String number;
    private Object orderDispatcherComment;
    private String total;
    private String retailerName;
    private boolean isTerminalState;
    private PaymentSHP payment;
    private String state;
    private ReplacementPolicySHP replacementPolicy;
    private String deliveryDate;
    private boolean canChangePaymentTool;
    private String email;
    private String lifePayPaymentMethod;
    private String cost;
    private AddressSHP address;
    private boolean isAlcohol;
    private String retailer;
    private List<Object> linkedShipments;
    private Object retailerCard;
    private int weight;
    private Object assemblyState;
    private String assemblyDeadlineAt;
    private int storeId;
    private Object dispatcherComment;
    private int itemCount;
    private boolean isPaid;
    private String itemTotal;
    private String deliveryBefore;
    private String tenantId;
    private Object totalGreaterThanReceipt;
    private String assemblyDeadlineStatus;
    private boolean isFirstOrder;
    private boolean isOnlinePayment;
    private String adjustmentTotal;
}