package ru.instamart.api.model.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SlotDC extends BaseObject {
    private String id;
    private Integer available;
    private Integer cost;
    private DeliveryWindowDC deliveryWindow;
    private ItemsWeightDC itemsWeight;
}
