package ru.instamart.api.model.shopper.app.active_shipments;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Null;

public class Attributes{

	@JsonProperty("logisticVolume")
	private String logisticVolume;

	@JsonProperty("inspectionResult")
	private Object inspectionResult;

	@JsonProperty("canBePrinted")
	private Object canBePrinted;

	@JsonProperty("customerComment")
	private Object customerComment;

	@JsonProperty("storeImportKey")
	private String storeImportKey;

	@JsonProperty("closingDocsRequired")
	private Boolean closingDocsRequired;

	@JsonProperty("isOutdated")
	private Boolean isOutdated;

	@JsonProperty("deliveryInterval")
	private String deliveryInterval;

	@JsonProperty("isPickup")
	private Boolean isPickup;

	@JsonProperty("deliveryDeadlineAt")
	private String deliveryDeadlineAt;

	@JsonProperty("uuid")
	private String uuid;

	@JsonProperty("number")
	private String number;

	@JsonProperty("orderDispatcherComment")
	private Object orderDispatcherComment;

	@JsonProperty("total")
	private String total;

	@JsonProperty("retailerName")
	private String retailerName;

	@JsonProperty("isTerminalState")
	private Boolean isTerminalState;

	@JsonProperty("payment")
	private Payment payment;

	@JsonProperty("state")
	private String state;

	@JsonProperty("replacementPolicy")
	private ReplacementPolicy replacementPolicy;

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("canChangePaymentTool")
	private Boolean canChangePaymentTool;

	@JsonProperty("email")
	private String email;

	@JsonProperty("expressDelivery")
	private Boolean expressDelivery;

	@Null
	@JsonProperty("lifePayPaymentMethod")
	private String lifePayPaymentMethod;

	@JsonProperty("cost")
	private String cost;

	@JsonProperty("address")
	private Address address;

	@JsonProperty("isAlcohol")
	private Boolean isAlcohol;

	@JsonProperty("retailer")
	private String retailer;

	@JsonProperty("linkedShipments")
	private List<Object> linkedShipments;

	@JsonProperty("retailerCard")
	private Object retailerCard;

	@JsonProperty("weight")
	private Integer weight;

	@JsonProperty("assemblyDeadlineAt")
	private String assemblyDeadlineAt;

	@JsonProperty("assemblyState")
	private Object assemblyState;

	@JsonProperty("storeId")
	private Integer storeId;

	@JsonProperty("hasPromotedItems")
	private Boolean hasPromotedItems;

	@JsonProperty("dispatcherComment")
	private Object dispatcherComment;

	@JsonProperty("itemCount")
	private Integer itemCount;

	@JsonProperty("isPaid")
	private Boolean isPaid;

	@JsonProperty("itemTotal")
	private String itemTotal;

	@JsonProperty("deliveryBefore")
	private String deliveryBefore;

	@JsonProperty("tenantId")
	private String tenantId;

	@JsonProperty("totalGreaterThanReceipt")
	private Object totalGreaterThanReceipt;

	@JsonProperty("assemblyDeadlineStatus")
	private String assemblyDeadlineStatus;

	@JsonProperty("isFirstOrder")
	private Boolean isFirstOrder;

	@JsonProperty("isOnlinePayment")
	private Boolean isOnlinePayment;

	@JsonProperty("adjustmentTotal")
	private String adjustmentTotal;
}