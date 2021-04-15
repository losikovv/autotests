package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemAttributesSHP extends BaseObject {
    private Integer assemblyId;
    private Object issue;
    private Object issueMessage;
    private String packs;
    private Integer itemsPerPack;
    private Integer quantity;
    private Integer foundQuantity;
    private Double size;
    private String humanSize;
    private Double weight;
    private Object thumbnail;
    private String price;
    private String amount;
    private String retailerPrice;
    private String retailerAmount;
    private String humanVolume;
    private Integer pickupOrder;
    private String name;
    private String retailerName;
    private String retailerSku;
    private String productSku;
    private String state;
    private Double totalWeight;
    private String pricerType;
    private Integer qty;
    private Object foundQty;
    private List<Object> foundQtys = null;
    private Integer originalQuantity;
    private String pcs;
    private String productionDate;
    private String bestBefore;
    private Integer vatRate;
    private String unit;
    private String priceWithDiscount;
    private String updatedAt;
    private List<String> eans = null;
    private Boolean isTouched;
    private Boolean isMarked;
    private Object cancelReason;
    private Object replacement;
    private Object fromItemId;
    private Object additionalItemForReplacementId;
    private Object clarificationReason;
}
