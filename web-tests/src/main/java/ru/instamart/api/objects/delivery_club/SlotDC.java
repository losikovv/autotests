package ru.instamart.api.objects.delivery_club;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SlotDC extends BaseObject {
    public String id;
    public Integer available;
    public Integer cost;
    public DeliveryWindowDC deliveryWindow;
    public ItemsWeightDC itemsWeight;
}
