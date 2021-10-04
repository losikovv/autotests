package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShipmentsItemSHP extends BaseObject {
    private String logisticVolume;
    private Object inspectionResult;
    private Object canBePrinted;
    private String customerComment;
    private String storeImportKey;
    private Boolean closingDocsRequired;
    private Boolean isOutdated;
    private String deliveryInterval;
    private Boolean isPickup;
    private String deliveryDeadlineAt;
    private String uuid;
    private String number;
    private Object orderDispatcherComment;
    private String total;
    private String retailerName;
    private Boolean isTerminalState;
    private PaymentSHP payment;
    private Integer id;
    private String state;
    private ReplacementPolicySHP replacementPolicy;
    private String deliveryDate;
    private Boolean canChangePaymentTool;
    private String email;
    private String lifePayPaymentMethod;
    private String cost;
    private AddressSHP address;
    private Boolean isAlcohol;
    private String retailer;
    private List<Object> linkedShipments;
    private Object retailerCard;
    private Integer weight;
    private Object assemblyState;
    private String assemblyDeadlineAt;
    private Integer storeId;
    private Object dispatcherComment;
    private Integer itemCount;
    private Boolean isPaid;
    private String itemTotal;
    private String deliveryBefore;
    private String tenantId;
    private Object totalGreaterThanReceipt;
    private String assemblyDeadlineStatus;
    private Object logisticAttributes;
    private Boolean isFirstOrder;
    private Boolean isOnlinePayment;
    private String adjustmentTotal;
}