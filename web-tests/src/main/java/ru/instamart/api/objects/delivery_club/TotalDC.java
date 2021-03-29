package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalDC extends BaseObject {
    private String totalPrice;
    private String discountTotalPrice;
    private String deliveryPrice;
}
