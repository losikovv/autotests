package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class TotalDC extends BaseObject {
    private String totalPrice;
    private String discountTotalPrice;
    private String deliveryPrice;
}
