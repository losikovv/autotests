package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotDC extends BaseObject {
    public String id;
    public Integer available;
    public Integer cost;
    public DeliveryWindowDC deliveryWindow;
    public ItemsWeightDC itemsWeight;
}
