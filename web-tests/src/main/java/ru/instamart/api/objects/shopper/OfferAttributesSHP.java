package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OfferAttributesSHP extends BaseObject {
    private String uuid;
    private String name;
    private String retailerSku;
    private String productSku;
    private String productName;
    private Double retailerPrice;
    private Integer retailerId;
    private String thumbnail;
    private Integer itemsPerPack;
    private Double size;
    private Double price;
    private Integer pickupOrder;
    private String humanVolume;
    private String pricerType;
    private Integer availableStock;
    private List<String> eans = null;
}
